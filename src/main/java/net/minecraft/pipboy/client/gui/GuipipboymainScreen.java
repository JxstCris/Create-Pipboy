package net.minecraft.pipboy.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.pipboy.world.inventory.GuipipboymainMenu;
import net.minecraft.pipboy.procedures.*;
import net.minecraft.pipboy.network.GuipipboymainButtonMessage;
import net.minecraft.pipboy.init.PipboyModScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import com.mojang.blaze3d.systems.RenderSystem;

public class GuipipboymainScreen extends AbstractContainerScreen<GuipipboymainMenu> implements PipboyModScreens.ScreenAccessor {
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    private boolean menuStateUpdateActive = false;
    private Button button_empty;
    private Button button_empty1;
    private Button button_empty2;
    private static final ResourceLocation IMAGE_0 = ResourceLocation.parse("pipboy:textures/screens/data_screen.png");
    private static final ResourceLocation IMAGE_1 = ResourceLocation.parse("pipboy:textures/screens/item_screen.png");
    private static final ResourceLocation IMAGE_2 = ResourceLocation.parse("pipboy:textures/screens/stat_screen.png");

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
    public void updateMenuState(int elementType, String name, Object elementState) {
        menuStateUpdateActive = true;
        menuStateUpdateActive = false;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (ConditionshowitemProcedure.execute(entity)) {
            // Pestaña ITEM: Renderiza slots, ítems del inventario, ítem sostenido y tooltips
            super.render(guiGraphics, mouseX, mouseY, partialTicks);
            this.renderTooltip(guiGraphics, mouseX, mouseY);
        } else {
            // Pestañas STAT y DATA: Renderiza manualmente fondo oscuro, botones y textos, omitiendo los slots
            this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
            this.renderBg(guiGraphics, partialTicks, mouseX, mouseY);

            for (var widget : this.renderables) {
                widget.render(guiGraphics, mouseX, mouseY, partialTicks);
            }

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((float) this.leftPos, (float) this.topPos, 0.0F);
            this.renderLabels(guiGraphics, mouseX, mouseY);
            guiGraphics.pose().popPose();
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        if (ConditionshowdataProcedure.execute(entity)) {
            guiGraphics.blit(IMAGE_0, this.leftPos + 0, this.topPos + 0, 0, 0, 336, 236, 336, 236);
        }
        if (ConditionshowitemProcedure.execute(entity)) {
            guiGraphics.blit(IMAGE_1, this.leftPos + 0, this.topPos + 0, 0, 0, 336, 236, 336, 236);
        }
        if (ConditionshowstatProcedure.execute(entity)) {
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
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (ConditionshowstatProcedure.execute(entity))
            guiGraphics.drawString(this.font, HpprocedureProcedure.execute(entity), 123, 28, -13369600, false);
        if (ConditionshowstatProcedure.execute(entity))
            guiGraphics.drawString(this.font, FoodprocedureProcedure.execute(entity), 172, 28, -13369600, false);
        if (ConditionshowstatProcedure.execute(entity))
            guiGraphics.drawString(this.font, XpprocedureProcedure.execute(entity), 224, 28, -13369600, false);
        if (ConditionshowstatProcedure.execute(entity))
            guiGraphics.drawString(this.font, DaysCounterProcedure.execute(world), 90, 97, -13369600, false);
        if (ConditionshowstatProcedure.execute(entity))
            guiGraphics.drawString(this.font, ArmorprocedureProcedure.execute(entity), 212, 74, -13369600, false);
    }

    @Override
    public void init() {
        super.init();
        button_empty = new PlainTextButton(this.leftPos + 129, this.topPos + 124, 25, 20, Component.translatable("gui.pipboy.guipipboymain.button_empty"), e -> {
            int x = GuipipboymainScreen.this.x;
            int y = GuipipboymainScreen.this.y;
            if (true) {
                PacketDistributor.sendToServer(new GuipipboymainButtonMessage(0, x, y, z));
                GuipipboymainButtonMessage.handleButtonAction(entity, 0, x, y, z);
            }
        }, this.font);
        this.addRenderableWidget(button_empty);
        button_empty1 = new PlainTextButton(this.leftPos + 159, this.topPos + 124, 25, 20, Component.translatable("gui.pipboy.guipipboymain.button_empty1"), e -> {
            int x = GuipipboymainScreen.this.x;
            int y = GuipipboymainScreen.this.y;
            if (true) {
                PacketDistributor.sendToServer(new GuipipboymainButtonMessage(1, x, y, z));
                GuipipboymainButtonMessage.handleButtonAction(entity, 1, x, y, z);
            }
        }, this.font);
        this.addRenderableWidget(button_empty1);
        button_empty2 = new PlainTextButton(this.leftPos + 190, this.topPos + 124, 25, 20, Component.translatable("gui.pipboy.guipipboymain.button_empty2"), e -> {
            int x = GuipipboymainScreen.this.x;
            int y = GuipipboymainScreen.this.y;
            if (true) {
                PacketDistributor.sendToServer(new GuipipboymainButtonMessage(2, x, y, z));
                GuipipboymainButtonMessage.handleButtonAction(entity, 2, x, y, z);
            }
        }, this.font);
        this.addRenderableWidget(button_empty2);
    }
}