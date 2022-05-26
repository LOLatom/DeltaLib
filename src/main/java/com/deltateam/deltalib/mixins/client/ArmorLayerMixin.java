package com.deltateam.deltalib.mixins.client;

import com.deltateam.deltalib.API.rendering.armor.IRenderAsArmor;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import net.minecraft.client.model.Model;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {
	@Inject(at = @At("TAIL"), method = "renderArmorPiece")
	public void renderArmor(PoseStack matrixStackIn, MultiBufferSource bufferIn, T entityLivingBaseIn, EquipmentSlot slotIn, int packedLightIn, A defaultModel, CallbackInfo ci) {
		ItemStack itemStack = entityLivingBaseIn.getItemBySlot(slotIn);
		if (itemStack.getItem() instanceof IRenderAsArmor armorItem) {
			if (slotIn == armorItem.getEquipmentSlot(itemStack)) {
				armorItem.render(bufferIn, itemStack, entityLivingBaseIn, matrixStackIn, packedLightIn);
				if (itemStack.hasFoil())
					armorItem.renderGlint(((RenderBuffers) bufferIn).bufferSource(), itemStack, entityLivingBaseIn, matrixStackIn, packedLightIn);
			}
		}
	}

//	@Inject(at = @At("HEAD"), method = "getArmorModelHook", cancellable = true)
//	public void preGetModel(T entity, ItemStack itemStack, EquipmentSlot slot, A model, CallbackInfoReturnable<Model> cir) {
//		if (itemStack.getItem() instanceof IRenderAsArmor armorItem) {
//			if (slot == armorItem.getEquipmentSlot(itemStack))
//				cir.setReturnValue(armorItem.getArmorModel(entity, itemStack, slot, model));
//		}
//	}
}
