package com.totis.infinityg.extras.totis_tickrate_changer.network;

import com.totis.infinityg.Main;
import com.totis.infinityg.extras.totis_tickrate_changer.TickrateAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@SuppressWarnings("all")
public class TickrateMessage {

    /*
    Add this in your ModMessages class, NetworkDirection as "CLIENT":
    INSTANCE.messageBuilder(TickrateMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TickrateMessage::new)
                .encoder(TickrateMessage::write)
                .consumer(TickrateMessage::handle)
                .add();
     */
    private float tickrate;

    public TickrateMessage(float tickrate) {
        this.tickrate = tickrate;
    }

    // Read
    public TickrateMessage(FriendlyByteBuf buf) {
        tickrate = buf.readFloat();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(tickrate);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            // This is the client

            // Client Player
            Player clientPlayer = Minecraft.getInstance().player;
            // Server Player
            ServerPlayer serverPlayer = context.getSender();
            float tickrate = this.tickrate;
            /*if(tickrate < Constants.MIN_TICKRATE) {
                Main.LOGGER.info("Tickrate forced to change from " + tickrate + " to " +
                        Constants.MIN_TICKRATE + ", because the value is too low");
                tickrate = Constants.MIN_TICKRATE;
            } else if(tickrate > Constants.MAX_TICKRATE) {
                Main.LOGGER.info("Tickrate forced to change from " + tickrate + " to " +
                        Constants.MAX_TICKRATE + ", because the value is too high");
                tickrate = Constants.MAX_TICKRATE;
            }*/

            if(context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                Main.INSTANCE.updateClientTickrate(tickrate, true);
            } else {
                TickrateAPI.changeTickrate(tickrate, true);
            }
        });
        return true;
    }
}
