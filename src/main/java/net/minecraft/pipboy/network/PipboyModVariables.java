package net.minecraft.pipboy.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.pipboy.PipboyMod;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import java.util.function.Supplier;

@EventBusSubscriber
public class PipboyModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, PipboyMod.MODID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(PlayerVariables::new).build());

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		PipboyMod.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
	}

	@SubscribeEvent
	public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player)
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
	}

	@SubscribeEvent
	public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
		if (event.getEntity() instanceof ServerPlayer player)
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
	}

	@SubscribeEvent
	public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player)
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
	}

	@SubscribeEvent
	public static void onPlayerTickUpdateSyncPlayerVariables(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player && player.getData(PLAYER_VARIABLES)._syncDirty) {
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
			player.getData(PLAYER_VARIABLES)._syncDirty = false;
		}
	}

	@SubscribeEvent
	public static void clonePlayer(PlayerEvent.Clone event) {
		PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
		PlayerVariables clone = new PlayerVariables();
		clone.pipboy_last_page = original.pipboy_last_page;
		clone.pipboy_tab = original.pipboy_tab;
		clone.pipboy_data_tab = original.pipboy_data_tab;
		clone.pipboy_data_page = original.pipboy_data_page;
		clone.radar_p1_x = original.radar_p1_x;
		clone.radar_p1_z = original.radar_p1_z;
		clone.player_p1_set = original.player_p1_set;
		clone.radar_p2_x = original.radar_p2_x;
		clone.radar_p2_z = original.radar_p2_z;
		clone.player_p2_set = original.player_p2_set;
		clone.radar_p3_x = original.radar_p3_x;
		clone.radar_p3_z = original.radar_p3_z;
		clone.player_p3_set = original.player_p3_set;
		if (!event.isWasDeath()) {
		}
		event.getEntity().setData(PLAYER_VARIABLES, clone);
	}

	public static class PlayerVariables implements INBTSerializable<CompoundTag> {
		boolean _syncDirty = false;
		public String pipboy_last_page = "\"stat\"";
		public double pipboy_tab = 0.0;
		public double pipboy_data_tab = 0;
		public double pipboy_data_page = 0;
		public double radar_p1_x = 0;
		public double radar_p1_z = 0;
		public boolean player_p1_set = false;
		public double radar_p2_x = 0;
		public double radar_p2_z = 0;
		public boolean player_p2_set = false;
		public double radar_p3_x = 0;
		public double radar_p3_z = 0;
		public boolean player_p3_set = false;

		@Override
		public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
			CompoundTag nbt = new CompoundTag();
			nbt.putString("pipboy_last_page", pipboy_last_page);
			nbt.putDouble("pipboy_tab", pipboy_tab);
			nbt.putDouble("pipboy_data_tab", pipboy_data_tab);
			nbt.putDouble("pipboy_data_page", pipboy_data_page);
			nbt.putDouble("radar_p1_x", radar_p1_x);
			nbt.putDouble("radar_p1_z", radar_p1_z);
			nbt.putBoolean("player_p1_set", player_p1_set);
			nbt.putDouble("radar_p2_x", radar_p2_x);
			nbt.putDouble("radar_p2_z", radar_p2_z);
			nbt.putBoolean("player_p2_set", player_p2_set);
			nbt.putDouble("radar_p3_x", radar_p3_x);
			nbt.putDouble("radar_p3_z", radar_p3_z);
			nbt.putBoolean("player_p3_set", player_p3_set);
			return nbt;
		}

		@Override
		public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
			pipboy_last_page = nbt.getString("pipboy_last_page");
			pipboy_tab = nbt.getDouble("pipboy_tab");
			pipboy_data_tab = nbt.getDouble("pipboy_data_tab");
			pipboy_data_page = nbt.getDouble("pipboy_data_page");
			radar_p1_x = nbt.getDouble("radar_p1_x");
			radar_p1_z = nbt.getDouble("radar_p1_z");
			player_p1_set = nbt.getBoolean("player_p1_set");
			radar_p2_x = nbt.getDouble("radar_p2_x");
			radar_p2_z = nbt.getDouble("radar_p2_z");
			player_p2_set = nbt.getBoolean("player_p2_set");
			radar_p3_x = nbt.getDouble("radar_p3_x");
			radar_p3_z = nbt.getDouble("radar_p3_z");
			player_p3_set = nbt.getBoolean("player_p3_set");
		}

		public void markSyncDirty() {
			_syncDirty = true;
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
		public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PipboyMod.MODID, "player_variables_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec
				.of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess())), (RegistryFriendlyByteBuf buffer) -> {
					PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
					message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
					return message;
				});

		@Override
		public Type<PlayerVariablesSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> context.player().getData(PLAYER_VARIABLES).deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess()))).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}