package com.deltateam.deltalib.API;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Basically, this is here because in vanilla MC,
 * you MUST extend ArmorItem and you can't use multiple
 * textures on one piece of armor
 */
public interface IRenderAsArmor {
	@OnlyIn(Dist.CLIENT)
	void render(IRenderTypeBuffer buffer, ItemStack stack, LivingEntity entity, MatrixStack matrixStack, int packedLightIn);
	
	EquipmentSlotType getEquipmentSlot(ItemStack itemstack);
	
	<A extends BipedModel<LivingEntity>> A getArmorModel(LivingEntity entityLivingBaseIn, ItemStack itemstack, EquipmentSlotType slotIn, BipedModel modelArmor);
}
