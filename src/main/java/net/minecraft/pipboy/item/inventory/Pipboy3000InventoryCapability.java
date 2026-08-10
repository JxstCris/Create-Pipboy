package net.minecraft.pipboy.item.inventory;

import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.pipboy.world.inventory.GuipipboymainMenu;
import net.minecraft.pipboy.init.PipboyModItems;
import net.minecraft.core.component.DataComponents;

import javax.annotation.Nonnull;

@EventBusSubscriber
public class Pipboy3000InventoryCapability extends ComponentItemHandler {
	@SubscribeEvent
	public static void onItemDropped(ItemTossEvent event) {
		if (event.getEntity().getItem().getItem() == PipboyModItems.PIPBOY_3000.get()) {
			Player player = event.getPlayer();
			if (player.containerMenu instanceof GuipipboymainMenu)
				player.closeContainer();
		}
	}

	public Pipboy3000InventoryCapability(MutableDataComponentHolder parent) {
		super(parent, DataComponents.CONTAINER, 36);
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

	@Override
	public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
		return stack.getItem() != PipboyModItems.PIPBOY_3000.get();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return super.getStackInSlot(slot).copy();
	}
}