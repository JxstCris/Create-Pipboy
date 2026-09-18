package net.minecraft.pipboy.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.pipboy.world.inventory.GuipipboymainMenu;
import net.minecraft.pipboy.procedures.*; 
import net.minecraft.pipboy.PipboyMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GuipipboymainButtonMessage {
    private final int buttonID, x, y, z;

    public GuipipboymainButtonMessage(FriendlyByteBuf buffer) {
        this.buttonID = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }

    public GuipipboymainButtonMessage(int buttonID, int x, int y, int z) {
        this.buttonID = buttonID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void buffer(GuipipboymainButtonMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.buttonID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
    }

    public static void handler(GuipipboymainButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player entity = context.getSender();
            int buttonID = message.buttonID;
            int x = message.x;
            int y = message.y;
            int z = message.z;
            handleButtonAction(entity, buttonID, x, y, z);
        });
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
        Level world = entity.level();
        HashMap guistate = GuipipboymainMenu.guistate;
        
        // Medida de seguridad nativa de Forge 1.20.1
        if (!world.hasChunkAt(new BlockPos(x, y, z)))
            return;
            
        // Cambio de pestañas - CORREGIDO PARA 1.20.1
        if (buttonID == 0) {
            StatbuttonProcedure.execute(entity);
        }
        if (buttonID == 1) {
            ItembuttonProcedure.execute(entity);
        }
        if (buttonID == 2) {
            DatabuttonProcedure.execute(entity);
        }
        
        // Radar 2D: Guardar y Borrar Puntos (Se mantienen igual)
        if (buttonID == 3) {
            Savpnt1Procedure.execute(x, z, entity);
        }
        if (buttonID == 4) {
            Delpnt1Procedure.execute(entity);
        }
        if (buttonID == 5) {
            Savpnt2Procedure.execute(x, z, entity);
        }
        if (buttonID == 6) {
            Delpnt2Procedure.execute(entity);
        }
        if (buttonID == 7) {
            Savpnt3Procedure.execute(x, z, entity);
        }
        if (buttonID == 8) {
            Delpnt3Procedure.execute(entity);
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        PipboyMod.addNetworkMessage(GuipipboymainButtonMessage.class, GuipipboymainButtonMessage::buffer, GuipipboymainButtonMessage::new, GuipipboymainButtonMessage::handler);
    }
}