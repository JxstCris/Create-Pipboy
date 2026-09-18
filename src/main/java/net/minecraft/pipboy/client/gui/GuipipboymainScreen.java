package net.minecraft.pipboy.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.pipboy.world.inventory.GuipipboymainMenu;
import net.minecraft.pipboy.procedures.*;
import net.minecraft.pipboy.network.GuipipboymainButtonMessage;
import net.minecraft.pipboy.init.PipboyModScreens;
import net.minecraft.pipboy.PipboyMod; // Importante para enviar paquetes en 1.20.1
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import com.mojang.blaze3d.systems.RenderSystem;

public class GuipipboymainScreen extends AbstractContainerScreen<GuipipboymainMenu> {
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    private Button button_empty;
    private Button button_empty1;
    private Button button_empty2;
    private Button button_sp1;
    private Button button_dp1;
    private Button button_sp2;
    private Button button_dp2;
    private Button button_sp3;
    private Button button_dp3;
    
    // Adaptado a Forge 1.20.1
    private static final ResourceLocation IMAGE_0 = new ResourceLocation("pipboy:textures/screens/stat_screen.png");
    private static final ResourceLocation IMAGE_1 = new ResourceLocation("pipboy:textures/screens/item_screen.png");
    private static final ResourceLocation IMAGE_2 = new ResourceLocation("pipboy:textures/screens/data_screen.png");

    public GuipipboymainScreen(GuipipboymainMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 336;
        this.imageHeight = 236;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        boolean showItem = ConditionshowitemProcedure.execute(entity);

        if (showItem) {
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
            this.renderTooltip(guiGraphics, mouseX, mouseY);
        } else {
            this.renderBackground(guiGraphics); // Adaptado a Forge 1.20.1
            this.renderBg(guiGraphics, partialTicks, mouseX, mouseY);

            for (Renderable renderable : this.renderables) {
                renderable.render(guiGraphics, mouseX, mouseY, partialTicks);
            }
        }

        this.renderCustomLabels(guiGraphics);
    }

    private void renderCustomLabels(GuiGraphics guiGraphics) {
        int colorGreen = (int) 0xFF00FF21L;

        if (ConditionshowstatProcedure.execute(entity)) {
            guiGraphics.drawString(this.font, HpprocedureProcedure.execute(entity), this.leftPos + 123, this.topPos + 28, colorGreen, false);
            guiGraphics.drawString(this.font, FoodprocedureProcedure.execute(entity), this.leftPos + 171, this.topPos + 28, colorGreen, false);
            guiGraphics.drawString(this.font, XpprocedureProcedure.execute(entity), this.leftPos + 224, this.topPos + 28, colorGreen, false);
            guiGraphics.drawString(this.font, DaysCounterProcedure.execute(world), this.leftPos + 90, this.topPos + 97, colorGreen, false);
            guiGraphics.drawString(this.font, ArmorprocedureProcedure.execute(entity), this.leftPos + 212, this.topPos + 74, colorGreen, false);
        }

        if (ConditionshowdataProcedure.execute(entity)) {
            int playerX = (int) entity.getX();
            int playerZ = (int) entity.getZ();

            guiGraphics.drawString(this.font, "DIR: " + entity.getDirection().name(), this.leftPos + 138, this.topPos + 22, colorGreen, false);
            guiGraphics.drawString(this.font, "POS: " + playerX + ", " + playerZ, this.leftPos + 135, this.topPos + 32, colorGreen, false);

            var nbt = entity.getPersistentData();
            int startY = 48;

            for (int i = 1; i <= 3; i++) {
                boolean isSet = nbt.getBoolean("player_p" + i + "_set");

                if (isSet) {
                    double px = nbt.getDouble("radar_p" + i + "_x");
                    double pz = nbt.getDouble("radar_p" + i + "_z");

                    int dist = (int) Math.sqrt(Math.pow(px - playerX, 2) + Math.pow(pz - playerZ, 2));

                    guiGraphics.drawString(this.font, "> P" + i + ": [" + (int) px + ", " + (int) pz + "]", this.leftPos + 90, this.topPos + startY, colorGreen, false);
                    guiGraphics.drawString(this.font, "  Distance: " + dist + "m", this.leftPos + 90, this.topPos + startY + 10, colorGreen, false);
                } else {
                    guiGraphics.drawString(this.font, "> P" + i + ": [EMPTY]", this.leftPos + 90, this.topPos + startY, -6710887, false);
                }
                startY += 22;
            }
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        if (ConditionshowstatProcedure.execute(entity)) {
            guiGraphics.blit(IMAGE_0, this.leftPos + 0, this.topPos + 0, 0, 0, 336, 236, 336, 236);
        }
        if (ConditionshowitemProcedure.execute(entity)) {
            guiGraphics.blit(IMAGE_1, this.leftPos + 0, this.topPos + 0, 0, 0, 336, 236, 336, 236);
        }
        if (ConditionshowdataProcedure.execute(entity)) {
            guiGraphics.blit(IMAGE_2, this.leftPos + 0, this.topPos + 0, 0, 0, 336, 236, 336, 236);
        }
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    public void init() {
        super.init();
        
        // Red reemplazada por la estructura base de Forge 1.20.1 en MCreator
        button_empty = new PlainTextButton(this.leftPos + 129, this.topPos + 124, 25, 20, Component.translatable("gui.pipboy.guipipboymain.button_empty"), e -> {
            if (true) {
                PipboyMod.PACKET_HANDLER.sendToServer(new GuipipboymainButtonMessage(0, (int) entity.getX(), (int) entity.getY(), (int) entity.getZ()));
                GuipipboymainButtonMessage.handleButtonAction(entity, 0, (int) entity.getX(), (int) entity.getY(), (int) entity.getZ());
            }
        }, this.font);
        this.addRenderableWidget(button_empty);

        button_empty1 = new PlainTextButton(this.leftPos + 159, this.topPos + 124, 25, 20, Component.translatable("gui.pipboy.guipipboymain.button_empty1"), e -> {
            if (true) {
                PipboyMod.PACKET_HANDLER.sendToServer(new GuipipboymainButtonMessage(1, (int) entity.getX(), (int) entity.getY(), (int) entity.getZ()));
                GuipipboymainButtonMessage.handleButtonAction(entity, 1, (int) entity.getX(), (int) entity.getY(), (int) entity.getZ());
            }
        }, this.font);
        this.addRenderableWidget(button_empty1);

        button_empty2 = new PlainTextButton(this.leftPos + 190, this.topPos + 124, 25, 20, Component.translatable("gui.pipboy.guipipboymain.button_empty2"), e -> {
            if (true) {
                PipboyMod.PACKET_HANDLER.sendToServer(new GuipipboymainButtonMessage(2, (int) entity.getX(), (int) entity.getY(), (int) entity.getZ()));
                GuipipboymainButtonMessage.handleButtonAction(entity, 2, (int) entity.getX(), (int) entity.getY(), (int) entity.getZ());
            }
        }, this.font);
        this.addRenderableWidget(button_empty2);

        button_sp1 = Button.builder(Component.translatable("gui.pipboy.guipipboymain.button_sp1"), e -> {
            if (ConditionshowdataProcedure.execute(entity)) {
                int pX = (int) entity.getX();
                int pY = (int) entity.getY();
                int pZ = (int) entity.getZ();
                Savpnt1Procedure.execute(pX, pZ, entity);
                PipboyMod.PACKET_HANDLER.sendToServer(new GuipipboymainButtonMessage(3, pX, pY, pZ));
                GuipipboymainButtonMessage.handleButtonAction(entity, 3, pX, pY, pZ);
            }
        }).bounds(this.leftPos + 208, this.topPos + 44, 20, 20).build();
        this.addWidget(button_sp1);

        button_dp1 = Button.builder(Component.translatable("gui.pipboy.guipipboymain.button_dp1"), e -> {
            if (ConditionshowdataProcedure.execute(entity)) {
                int pX = (int) entity.getX();
                int pY = (int) entity.getY();
                int pZ = (int) entity.getZ();
                Delpnt1Procedure.execute(entity);
                PipboyMod.PACKET_HANDLER.sendToServer(new GuipipboymainButtonMessage(4, pX, pY, pZ));
                GuipipboymainButtonMessage.handleButtonAction(entity, 4, pX, pY, pZ);
            }
        }).bounds(this.leftPos + 230, this.topPos + 44, 20, 20).build();
        this.addWidget(button_dp1);

        button_sp2 = Button.builder(Component.translatable("gui.pipboy.guipipboymain.button_sp2"), e -> {
            if (ConditionshowdataProcedure.execute(entity)) {
                int pX = (int) entity.getX();
                int pY = (int) entity.getY();
                int pZ = (int) entity.getZ();
                Savpnt2Procedure.execute(pX, pZ, entity);
                PipboyMod.PACKET_HANDLER.sendToServer(new GuipipboymainButtonMessage(5, pX, pY, pZ));
                GuipipboymainButtonMessage.handleButtonAction(entity, 5, pX, pY, pZ);
            }
        }).bounds(this.leftPos + 208, this.topPos + 66, 20, 20).build();
        this.addWidget(button_sp2);

        button_dp2 = Button.builder(Component.translatable("gui.pipboy.guipipboymain.button_dp2"), e -> {
            if (ConditionshowdataProcedure.execute(entity)) {
                int pX = (int) entity.getX();
                int pY = (int) entity.getY();
                int pZ = (int) entity.getZ();
                Delpnt2Procedure.execute(entity);
                PipboyMod.PACKET_HANDLER.sendToServer(new GuipipboymainButtonMessage(6, pX, pY, pZ));
                GuipipboymainButtonMessage.handleButtonAction(entity, 6, pX, pY, pZ);
            }
        }).bounds(this.leftPos + 230, this.topPos + 66, 20, 20).build();
        this.addWidget(button_dp2);

        button_sp3 = Button.builder(Component.translatable("gui.pipboy.guipipboymain.button_sp3"), e -> {
            if (ConditionshowdataProcedure.execute(entity)) {
                int pX = (int) entity.getX();
                int pY = (int) entity.getY();
                int pZ = (int) entity.getZ();
                Savpnt3Procedure.execute(pX, pZ, entity);
                PipboyMod.PACKET_HANDLER.sendToServer(new GuipipboymainButtonMessage(7, pX, pY, pZ));
                GuipipboymainButtonMessage.handleButtonAction(entity, 7, pX, pY, pZ);
            }
        }).bounds(this.leftPos + 208, this.topPos + 88, 20, 20).build();
        this.addWidget(button_sp3);

        button_dp3 = Button.builder(Component.translatable("gui.pipboy.guipipboymain.button_dp3"), e -> {
            if (ConditionshowdataProcedure.execute(entity)) {
                int pX = (int) entity.getX();
                int pY = (int) entity.getY();
                int pZ = (int) entity.getZ();
                Delpnt3Procedure.execute(entity);
                PipboyMod.PACKET_HANDLER.sendToServer(new GuipipboymainButtonMessage(8, pX, pY, pZ));
                GuipipboymainButtonMessage.handleButtonAction(entity, 8, pX, pY, pZ);
            }
        }).bounds(this.leftPos + 230, this.topPos + 88, 20, 20).build();
        this.addWidget(button_dp3);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.button_sp1.visible = ConditionshowdataProcedure.execute(entity);
        this.button_dp1.visible = ConditionshowdataProcedure.execute(entity);
        this.button_sp2.visible = ConditionshowdataProcedure.execute(entity);
        this.button_dp2.visible = ConditionshowdataProcedure.execute(entity);
        this.button_sp3.visible = ConditionshowdataProcedure.execute(entity);
        this.button_dp3.visible = ConditionshowdataProcedure.execute(entity);
    }
}