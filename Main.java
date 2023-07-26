package com.totis.infinityg;

import com.mojang.logging.LogUtils;
import com.totis.infinityg.client.render.entity.BlackholeRenderer;
import com.totis.infinityg.client.render.entity.BlastRenderer;
import com.totis.infinityg.client.render.entity.PortalRenderer;
import com.totis.infinityg.client.render.entity.TornadoRenderer;
import com.totis.infinityg.server.entity.ModEntityTypes;
import com.totis.infinityg.server.item.ModItems;
import com.totis.infinityg.server.network.ModMessages;
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
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static Main INSTANCE;

    public Main() {
        INSTANCE = this;
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        bus.addListener(this::blockEntitySpecialRenderer);
        bus.addListener(this::setup);
        bus.addListener(this::enqueueIMC);
        bus.addListener(this::processIMC);
        bus.addListener(this::clientSetup);

        ModItems.register(bus);
        ModEntityTypes.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void blockEntitySpecialRenderer(final EntityRenderersEvent.RegisterRenderers event) {
        // Sync block entity with a custom renderer
        //event.registerBlockEntityRenderer(ModBlockEntities.VENDINGMACHINE_BLOCKENTITY.get(), VendingMachineRender::new);
    }
    private void clientSetup(final FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntityTypes.PORTAL.get(), PortalRenderer::new);
        EntityRenderers.register(ModEntityTypes.BLAST.get(), BlastRenderer::new);
        EntityRenderers.register(ModEntityTypes.TORNADO.get(), TornadoRenderer::new);
        EntityRenderers.register(ModEntityTypes.BLACKHOLE.get(), BlackholeRenderer::new);
        // 3D Blocks (render correctly)
        //ItemBlockRenderTypes.setRenderLayer(ModBlocks.ATM.get(), RenderType.cutout());

        // Sync the block entity's menu with its screen
        //MenuScreens.register(ModMenuTypes.ATM_MENU.get(), ATMScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ModMessages.register();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    @OnlyIn(Dist.CLIENT)
    public void updateClientTickrate(float tickrate, boolean log) {
        if(log) LOGGER.info("Updating client tickrate to " + tickrate);

        Constants.TICKS_PER_SECOND = tickrate;
        if(Constants.CHANGE_SOUND) { Constants.GAME_SPEED = tickrate / 20F; }

        Minecraft mc = Minecraft.getInstance();
        if(mc == null) return; // Wut

        mc.timer.msPerTick = 1000F / tickrate;
    }

    public void updateServerTickrate(float tickrate, boolean log) {
        if(log) LOGGER.info("Updating server tickrate to " + tickrate);

        Constants.MILISECONDS_PER_TICK = (long)(1000L / tickrate);
    }
}
