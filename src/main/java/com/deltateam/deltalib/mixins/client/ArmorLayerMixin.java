package com.deltateam.deltalib.mixins.client;

import com.deltateam.deltalib.API.rendering.armor.IRenderAsArmor;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedArmorLayer.class)
public abstract class ArmorLayerMixin<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> {
	@Shadow
	protected abstract void setPartVisibility(A p_188359_1_, EquipmentSlotType p_188359_2_);
	
	@Shadow
	@Final
	private A outerModel;
	
	@Inject(at = @At("HEAD"), method = "renderArmorPiece")
	public void renderArmor(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, T entityLivingBaseIn, EquipmentSlotType slotIn, int packedLightIn, A defaultModel, CallbackInfo ci) {
		ItemStack itemstack = entityLivingBaseIn.getItemBySlot(slotIn);
		if (itemstack.getItem() instanceof IRenderAsArmor) {
			IRenderAsArmor armoritem = (IRenderAsArmor) itemstack.getItem();
			if (armoritem.getEquipmentSlot(itemstack) == slotIn) {
				A a = (A) armoritem.getArmorModel(entityLivingBaseIn, itemstack, slotIn, outerModel);
				a = getArmorModelHook(entityLivingBaseIn, itemstack, slotIn, a);
				if (a != null) {
					((BipedModel) ((LayerRenderer<Entity, EntityModel<Entity>>) (Object) this).getParentModel()).copyPropertiesTo(a);
					float limbSwing = entityLivingBaseIn.swingTime;
					float limbSwingAmount = entityLivingBaseIn.swingTime;
					a.prepareMobModel(entityLivingBaseIn, limbSwing, limbSwingAmount, Minecraft.getInstance().getFrameTime());
					this.setPartVisibility(a, slotIn);
					a.setupAnim(entityLivingBaseIn, limbSwing, limbSwingAmount, entityLivingBaseIn.tickCount, entityLivingBaseIn.yRot, entityLivingBaseIn.xRot);
					armoritem.render(bufferIn, itemstack, entityLivingBaseIn, matrixStackIn, packedLightIn);
				}
			}
			if (ci.isCancellable()) {
				ci.cancel();
			}
		}
	}
	
	@Shadow
	protected abstract A getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlotType slot, A model);
}
