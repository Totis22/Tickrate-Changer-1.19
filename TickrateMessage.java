package com.totis.mymodid;

import com.totis.mymodid.Main;
import com.totis.mymodid.extras.totis_tickrate_changer.TickrateAPI;
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

            if(context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                Main.INSTANCE.updateClientTickrate(tickrate, true);
            } else {
                TickrateAPI.changeTickrate(tickrate, true);
            }
        });
        return true;
    }
}
