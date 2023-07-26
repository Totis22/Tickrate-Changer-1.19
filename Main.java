package com.totis.mymodid;

import com.mojang.logging.LogUtils;
import com.totis.mymodid.client.render.entity.BlackholeRenderer;
import com.totis.mymodid.client.render.entity.BlastRenderer;
import com.totis.mymodid.client.render.entity.PortalRenderer;
import com.totis.mymodid.client.render.entity.TornadoRenderer;
import com.totis.mymodid.server.entity.ModEntityTypes;
import com.totis.mymodid.server.item.ModItems;
import com.totis.mymodid.server.network.ModMessages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.stream.Collectors;

@SuppressWarnings("all")
@Mod(Constants.MOD_ID)
public class Main {

    public static final Logger LOGGER = LogUtils.getLogger();
    public static Main INSTANCE;

    public Main() {
        INSTANCE = this;
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        bus.addListener(this::setup);
        bus.addListener(this::enqueueIMC);
        bus.addListener(this::processIMC);
        bus.addListener(this::clientSetup);

        ModItems.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) { }

    private void setup(final FMLCommonSetupEvent event) {
        ModMessages.register();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) { }

    private void processIMC(final InterModProcessEvent event) { }

    @OnlyIn(Dist.CLIENT)
    public void updateClientTickrate(float tickrate, boolean log) {
        if(log) LOGGER.info("Updating client tickrate to " + tickrate);

        Constants.TICKS_PER_SECOND = tickrate;
        if(Constants.CHANGE_SOUND) { Constants.GAME_SPEED = tickrate / 20F; }

        Minecraft mc = Minecraft.getInstance();
        if(mc == null) return; // Wut

        //Timer is a private field, so i added it to accesstransformer.cfg
        /*
        public net.minecraft.client.Minecraft f_90991_ # timer
        public-f net.minecraft.client.Timer * # All fields
        */
        mc.timer.msPerTick = 1000F / tickrate;
    }

    public void updateServerTickrate(float tickrate, boolean log) {
        if(log) LOGGER.info("Updating server tickrate to " + tickrate);

        Constants.MILISECONDS_PER_TICK = (long)(1000L / tickrate);
    }
}
