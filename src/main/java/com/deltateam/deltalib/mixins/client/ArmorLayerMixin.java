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
	@Final
	protected A modelArmor;
	
	@Inject(at = @At("HEAD"), method = "func_241739_a_(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/inventory/EquipmentSlotType;ILnet/minecraft/client/renderer/entity/model/BipedModel;)V")
	public void renderArmor(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, T entityLivingBaseIn, EquipmentSlotType slotIn, int packedLightIn, A defaultModel, CallbackInfo ci) {
		ItemStack itemstack = entityLivingBaseIn.getItemStackFromSlot(slotIn);
		if (itemstack.getItem() instanceof IRenderAsArmor) {
			IRenderAsArmor armoritem = (IRenderAsArmor) itemstack.getItem();
			if (armoritem.getEquipmentSlot(itemstack) == slotIn) {
				A a = (A) armoritem.getArmorModel((LivingEntity) entityLivingBaseIn, itemstack, slotIn, (BipedModel) modelArmor);
				a = getArmorModelHook(entityLivingBaseIn, itemstack, slotIn, a);
				if (a != null) {
					((BipedModel) ((LayerRenderer<Entity, EntityModel<Entity>>) (Object) this).getEntityModel()).setModelAttributes(a);
					float limbSwing = entityLivingBaseIn.limbSwing;
					float limbSwingAmount = entityLivingBaseIn.limbSwingAmount;
					a.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, Minecraft.getInstance().getRenderPartialTicks());
					this.setModelSlotVisible(a, slotIn);
					a.setRotationAngles(entityLivingBaseIn, limbSwing, limbSwingAmount, entityLivingBaseIn.ticksExisted, entityLivingBaseIn.rotationYaw, entityLivingBaseIn.rotationPitch);
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
	
	@Shadow
	protected abstract void setModelSlotVisible(A modelIn, EquipmentSlotType slotIn);
}