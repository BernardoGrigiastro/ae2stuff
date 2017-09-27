/*
 * Copyright (c) bdew, 2014 - 2017
 * https://github.com/bdew/ae2stuff
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.ae2stuff

import net.bdew.ae2stuff.machines.encoder.MachineEncoder
import net.bdew.ae2stuff.machines.grower.MachineGrower
import net.bdew.ae2stuff.machines.inscriber.MachineInscriber
import net.bdew.ae2stuff.machines.wireless.MachineWireless
import net.bdew.lib.config.{BlockManager, MachineManager}

object Blocks extends BlockManager(CreativeTabs.main)

object Machines extends MachineManager(Tuning.getSection("Machines"), AE2Stuff.guiHandler, Blocks) {
  registerMachine(MachineEncoder)
  registerMachine(MachineGrower)
  registerMachine(MachineInscriber)
  registerMachine(MachineWireless)
}
