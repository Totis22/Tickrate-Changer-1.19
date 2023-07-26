package com.totis.infinityg.server.item;

import com.totis.infinityg.extras.totis_tickrate_changer.TickrateAPI;
import com.totis.infinityg.utils.TotisMathUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

@SuppressWarnings("all")
public class TimeWatchItem extends Item {

    private int wait = 0;
    private int MODE = 0;
    private boolean used = false;

    public TimeWatchItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if(!world.isClientSide()) {
            if(player.isShiftKeyDown() || player.isCrouching()) {
                ++MODE;
                if(MODE > 4) MODE = 1;
            }
            if(MODE == 1) player.getItemInHand(hand).setHoverName(new TextComponent("§ePAUSE"));
            else if(MODE == 2) player.getItemInHand(hand).setHoverName(new TextComponent("§eSLOWMO"));
            else if(MODE == 3) player.getItemInHand(hand).setHoverName(new TextComponent("§eFAST-FORWARD"));
            else if(MODE == 4) player.getItemInHand(hand).setHoverName(new TextComponent("§eRANDOM-SPEED"));

            if(!player.isShiftKeyDown() && !player.isCrouching()) {
                if(MODE == 2) {
                    if(!used) {
                        TickrateAPI.changeTickrate(7.0F, true);
                        //TickrateAPI.changePlayerTickrateToDefault(player, true);
                        used = true;
                    } else {
                        TickrateAPI.resetTickrate(true);
                        used = false;
                    }
                }
                if(MODE == 3) {
                    if(!used) {
                        TickrateAPI.changeTickrate(45F, true);
                        //TickrateAPI.changePlayerTickrateToDefault(player, true);
                        used = true;
                    } else {
                        TickrateAPI.resetTickrate(true);
                        used = false;
                    }
                }
                if(MODE == 4) {
                    Random rand = new Random();
                    float e = TotisMathUtils.randFloat(7.0F,40.0F);
                    if(!used) {
                        TickrateAPI.changeTickrate(e, true);
                        //TickrateAPI.changePlayerTickrateToDefault(player, true);
                        used = true;
                    } else {
                        TickrateAPI.resetTickrate(true);
                        used = false;
                    }
                }
            }

            //TickrateAPI.changeTickrate(20.0F); // Default
            //TickrateAPI.changeTickrate(40.0F); // Fast

        }
        return InteractionResultHolder.consume(stack);
    }
}
