package net.minecraft.pipboy.network;

import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.pipboy.PipboyMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PipboyModVariables {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		PipboyMod.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new, PlayerVariablesSyncMessage::handler);
	}

	@SubscribeEvent
	public static void init(RegisterCapabilitiesEvent event) {
		event.register(PlayerVariables.class);
	}

	@Mod.EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			event.getOriginal().revive();
			PlayerVariables original = ((PlayerVariables) event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
			PlayerVariables clone = ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
			clone.pipboy_last_page = original.pipboy_last_page;
			clone.pipboy_tab = original.pipboy_tab;
			clone.pipboy_data_tab = original.pipboy_data_tab;
			clone.pipboy_data_page = original.pipboy_data_page;
			clone.radar_p1_x = original.radar_p1_x;
			clone.radar_p1_z = original.radar_p1_z;
			clone.radar_p1_set = original.radar_p1_set;
			clone.radar_p2_x = original.radar_p2_x;
			clone.radar_p2_z = original.radar_p2_z;
			clone.radar_p2_set = original.radar_p2_set;
			clone.radar_p3_x = original.radar_p3_x;
			clone.radar_p3_z = original.radar_p3_z;
			clone.radar_p3_set = original.radar_p3_set;
			if (!event.isWasDeath()) {
			}
		}
	}

	public static final Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerVariables>() {
	});

	@Mod.EventBusSubscriber
	private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
		@SubscribeEvent
		public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
				event.addCapability(new ResourceLocation("pipboy", "player_variables"), new PlayerVariablesProvider());
		}

		private final PlayerVariables playerVariables = new PlayerVariables();
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public Tag serializeNBT() {
			return playerVariables.writeNBT();
		}

		@Override
		public void deserializeNBT(Tag nbt) {
			playerVariables.readNBT(nbt);
		}
	}

	public static class PlayerVariables {
		public String pipboy_last_page = "\"stat\"";
		public double pipboy_tab = 0.0;
		public double pipboy_data_tab = 0.0;
		public double pipboy_data_page = 0;
		public double radar_p1_x = 0;
		public double radar_p1_z = 0;
		public boolean radar_p1_set = false;
		public double radar_p2_x = 0;
		public double radar_p2_z = 0;
		public boolean radar_p2_set = false;
		public double radar_p3_x = 0;
		public double radar_p3_z = 0;
		public boolean radar_p3_set = false;

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer)
				PipboyMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
		}

		public Tag writeNBT() {
			CompoundTag nbt = new CompoundTag();
			nbt.putString("pipboy_last_page", pipboy_last_page);
			nbt.putDouble("pipboy_tab", pipboy_tab);
			nbt.putDouble("pipboy_data_tab", pipboy_data_tab);
			nbt.putDouble("pipboy_data_page", pipboy_data_page);
			nbt.putDouble("radar_p1_x", radar_p1_x);
			nbt.putDouble("radar_p1_z", radar_p1_z);
			nbt.putBoolean("radar_p1_set", radar_p1_set);
			nbt.putDouble("radar_p2_x", radar_p2_x);
			nbt.putDouble("radar_p2_z", radar_p2_z);
			nbt.putBoolean("radar_p2_set", radar_p2_set);
			nbt.putDouble("radar_p3_x", radar_p3_x);
			nbt.putDouble("radar_p3_z", radar_p3_z);
			nbt.putBoolean("radar_p3_set", radar_p3_set);
			return nbt;
		}

		public void readNBT(Tag Tag) {
			CompoundTag nbt = (CompoundTag) Tag;
			pipboy_last_page = nbt.getString("pipboy_last_page");
			pipboy_tab = nbt.getDouble("pipboy_tab");
			pipboy_data_tab = nbt.getDouble("pipboy_data_tab");
			pipboy_data_page = nbt.getDouble("pipboy_data_page");
			radar_p1_x = nbt.getDouble("radar_p1_x");
			radar_p1_z = nbt.getDouble("radar_p1_z");
			radar_p1_set = nbt.getBoolean("radar_p1_set");
			radar_p2_x = nbt.getDouble("radar_p2_x");
			radar_p2_z = nbt.getDouble("radar_p2_z");
			radar_p2_set = nbt.getBoolean("radar_p2_set");
			radar_p3_x = nbt.getDouble("radar_p3_x");
			radar_p3_z = nbt.getDouble("radar_p3_z");
			radar_p3_set = nbt.getBoolean("radar_p3_set");
		}
	}

	public static class PlayerVariablesSyncMessage {
		private final PlayerVariables data;

		public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
			this.data = new PlayerVariables();
			this.data.readNBT(buffer.readNbt());
		}

		public PlayerVariablesSyncMessage(PlayerVariables data) {
			this.data = data;
		}

		public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeNbt((CompoundTag) message.data.writeNBT());
		}

		public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
					variables.pipboy_last_page = message.data.pipboy_last_page;
					variables.pipboy_tab = message.data.pipboy_tab;
					variables.pipboy_data_tab = message.data.pipboy_data_tab;
					variables.pipboy_data_page = message.data.pipboy_data_page;
					variables.radar_p1_x = message.data.radar_p1_x;
					variables.radar_p1_z = message.data.radar_p1_z;
					variables.radar_p1_set = message.data.radar_p1_set;
					variables.radar_p2_x = message.data.radar_p2_x;
					variables.radar_p2_z = message.data.radar_p2_z;
					variables.radar_p2_set = message.data.radar_p2_set;
					variables.radar_p3_x = message.data.radar_p3_x;
					variables.radar_p3_z = message.data.radar_p3_z;
					variables.radar_p3_set = message.data.radar_p3_set;
				}
			});
			context.setPacketHandled(true);
		}
	}
}
