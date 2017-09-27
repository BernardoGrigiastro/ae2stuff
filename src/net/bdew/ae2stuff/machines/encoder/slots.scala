/*
 * Copyright (c) bdew, 2014 - 2017
 * https://github.com/bdew/ae2stuff
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.ae2stuff.machines.encoder

import net.bdew.lib.gui.SlotClickable
import net.bdew.lib.items.ItemUtils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{ClickType, IInventory, Slot}
import net.minecraft.item.ItemStack

class SlotFakeCrafting(inv: IInventory, slot: Int, x: Int, y: Int, onChanged: () => Unit) extends Slot(inv, slot, x, y) with SlotClickable {
  override def canTakeStack(p: EntityPlayer) = false

  override def onClick(clickType: ClickType, button: Int, player: EntityPlayer): ItemStack = {
    val newStack = if (!player.inventory.getItemStack.isEmpty) {
      val stackCopy = player.inventory.getItemStack.copy()
      stackCopy.setCount(1)
      stackCopy
    } else {
      ItemStack.EMPTY
    }

    if (!ItemStack.areItemStacksEqual(newStack, inventory.getStackInSlot(slot))) {
      inventory.setInventorySlotContents(slot, newStack)
      onChanged()
    }

    player.inventory.getItemStack
  }
}

class SlotFakeCraftingResult(inv: IInventory, slot: Int, x: Int, y: Int) extends Slot(inv, slot, x, y) {
  override def canTakeStack(p: EntityPlayer) = false
  override def isItemValid(s: ItemStack) = false
}

class SlotFakeEncodedPattern(inv: TileEncoder, slot: Int, x: Int, y: Int) extends Slot(inv, slot, x, y) with SlotClickable {

  override def onClick(clickType: ClickType, button: Int, player: EntityPlayer): ItemStack = {
    val encoded = inv.getStackInSlot(slot)
    if (!encoded.isEmpty) {
      if (clickType == ClickType.PICKUP) {
        if (player.inventory.getItemStack.isEmpty) {
          player.inventory.setItemStack(encoded.copy())
          inv.decrStackSize(inv.slots.patterns, 1)
        }
      } else if (clickType == ClickType.QUICK_MOVE) {
        if (ItemUtils.addStackToSlots(encoded.copy(), player.inventory, 0 until player.inventory.getSizeInventory, true).isEmpty)
          inv.decrStackSize(inv.slots.patterns, 1)
      }
    }
    player.inventory.getItemStack
  }
}