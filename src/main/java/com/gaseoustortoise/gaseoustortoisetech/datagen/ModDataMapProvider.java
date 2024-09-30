package com.gaseoustortoise.gaseoustortoisetech.datagen;

import com.gaseoustortoise.gaseoustortoisetech.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        // To add fuel
        this.builder(NeoForgeDataMaps.FURNACE_FUELS).add(ModItems.RAW_BISMUTH.getId(), new FurnaceFuel(1200), false);
    }
}
