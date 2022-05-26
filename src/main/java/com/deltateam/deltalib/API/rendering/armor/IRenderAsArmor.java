package com.deltateam.deltalib.API.rendering.armor;

import com.deltateam.deltalib.RedirectingBuffer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Basically, this is here because in vanilla MC,
 * you MUST extend ArmorItem and you can't use multiple
 * textures on one piece of armor
 */
public interface IRenderAsArmor {
	@OnlyIn(Dist.CLIENT)
	void render(MultiBufferSource buffer, ItemStack stack, LivingEntity entity, PoseStack matrixStack, int packedLightIn);
	EquipmentSlot getEquipmentSlot(ItemStack itemstack);
//	<A extends HumanoidModel<LivingEntity>> A getArmorModel(LivingEntity entityLivingBaseIn, ItemStack itemstack, EquipmentSlot slotIn, HumanoidModel modelArmor);
	default void renderGlint(MultiBufferSource bufferIn, ItemStack itemstack, LivingEntity entityLivingBaseIn, PoseStack matrixStackIn, int packedLightIn) {
		render(new RedirectingBuffer(bufferIn, RenderType.glint()), itemstack, entityLivingBaseIn, matrixStackIn, packedLightIn);
	}
}
