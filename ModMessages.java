package com.totis.mymodid;

import com.totis.mymodid.Constants;
import com.totis.mymodid.extras.totis_tickrate_changer.network.TickrateMessage;
import com.totis.mymodid.server.network.packets.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

@SuppressWarnings("all")
public class ModMessages {

    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int id() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Constants.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;
        
        INSTANCE.messageBuilder(TickrateMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TickrateMessage::new)
                .encoder(TickrateMessage::write)
                .consumer(TickrateMessage::handle)
                .add();
    }

    private static <T> void register(Class<T> clazz, IMessage<T> message) {
        INSTANCE.registerMessage(id(), clazz, message::encode, message::decode, message::handle);
    }
    public static <MSG> void sendToAllTrackingAndSelf(MSG message, Entity entity) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), message);
    }
    // Send to the server
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
    // Send to one player
    public static <MSG> void sendToClient(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> player), message);
    }
    // Send to all connected players
    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }


    public interface IMessage<T>
    {
        void encode(T message, FriendlyByteBuf buffer);

        T decode(FriendlyByteBuf buffer);

        void handle(T message, Supplier<NetworkEvent.Context> supplier);
    }
}
