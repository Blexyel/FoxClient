package net.foxes4life.foxclient.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.foxes4life.foxclient.Main;
import net.foxes4life.foxclient.ui.button.FoxClientButton;
import net.foxes4life.foxclient.ui.button.FoxClientMiniButton;
import net.foxes4life.foxclient.ui.toast.FoxClientToast;
import net.foxes4life.foxclient.util.BackgroundUtils;
import net.foxes4life.foxclient.util.TextUtils;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.toast.Toast;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.lang.reflect.InvocationTargetException;

@Environment(EnvType.CLIENT)
public class TitleScreen extends Screen {

    // ui
    private static final Identifier BUTTON_BOX = Identifier.of("foxclient", "textures/ui/title/titlebox.png");
    private static final Identifier FOMX = Identifier.of("foxclient", "textures/ui/branding/fox.png");
    // bg
    private static final Identifier EXIT_BUTTON = Identifier.of("foxclient", "textures/ui/title/buttons/exit.png");
    private static final Identifier OPTIONS_BUTTON = Identifier.of("foxclient", "textures/ui/title/buttons/options.png");
    private static final Identifier EMPTY_BUTTON = Identifier.of("foxclient", "textures/ui/title/buttons/empty.png");
    private static final Identifier ACCESSIBILITY_BUTTON = Identifier.of("foxclient", "textures/ui/title/buttons/accessibility.png");
    private static final Identifier MODS_BUTTON = Identifier.of("foxclient", "textures/ui/title/buttons/modmenu.png");
    private static final Identifier REALMS_BUTTON = Identifier.of("foxclient", "textures/ui/title/buttons/realms.png");
    private static final Identifier FOXCLIENT_OPTIONS_BUTTON = Identifier.of("foxclient", "textures/ui/title/buttons/tail.png");
    private static final Identifier DISCORD_BUTTON = Identifier.of("foxclient", "textures/ui/title/buttons/discord.png");
    //private static final Identifier REPLAYMOD_BUTTON = Identifier.of("foxclient", "textures/ui/buttons/empty.png");
    //private static final Identifier UPDATE_BUTTON = Identifier.of("foxclient", "textures/ui/buttons/empty.png");


    private static final Text mojangCopyrightText = TextUtils.string("Copyright Mojang AB. Do not distribute!");
    private int mojangCopyrightTextWidth;
    private int mojangCopyrightTextX;

    private static final String foxclientCopyrightText = "© FoxClient 2021-2025";
    private int foxclientCopyrightTextX;

    private final boolean doBackgroundFade;

    private long backgroundFadeStart;

    public TitleScreen(boolean doBackgroundFade) {
        super(TextUtils.string("FoxClient"));
        this.doBackgroundFade = doBackgroundFade;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    protected void init() {
        assert this.client != null;
//        this.client.keyboard.setRepeatEvents(true);

        int y = this.height / 2 + 10;
        int spacingY = 24;

        BackgroundUtils.selectBackground();

        // VANILLA BUTTONS
        this.addDrawableChild(new FoxClientButton(this.width / 2 - 100, y, 200, 20, TextUtils.translatable("menu.singleplayer"),
                (button) -> {
                    assert this.client != null;
                    this.client.setScreen(new SelectWorldScreen(this));
                })
        );

        this.addDrawableChild(new FoxClientButton(this.width / 2 - 100, y + spacingY, 200, 20, TextUtils.translatable("menu.multiplayer"), (button) -> this.client.setScreen(new MultiplayerScreen(this))));

        loadMiniButtons();

        // copyright
        this.mojangCopyrightTextWidth = this.textRenderer.getWidth(mojangCopyrightText);
        this.mojangCopyrightTextX = this.width - this.mojangCopyrightTextWidth - 4;

        int foxclientCopyrightTextWidth = this.textRenderer.getWidth(foxclientCopyrightText);
        this.foxclientCopyrightTextX = this.width - foxclientCopyrightTextWidth - 4;
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        // do nothing because no lol (not overriding this will break the whole ui)
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.backgroundFadeStart == 0L && this.doBackgroundFade) {
            this.backgroundFadeStart = Util.getMeasuringTimeMs();
        }
        float f = this.doBackgroundFade ? (float) (Util.getMeasuringTimeMs() - this.backgroundFadeStart) / 1000.0F : 1.0F;

        // draw background
        BackgroundUtils.drawRandomBackground(context, this.width, this.height);

        // draw button box
        /*
         - these seem to not be needed anymore
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, BUTTON_BOX);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.doBackgroundFade ? (float) MathHelper.ceil(MathHelper.clamp(f, 0.0F, 1.0F)) : 1.0F);
        */
        context.drawTexture(BUTTON_BOX, (width / 2) - (250 / 2), height / 2 - (250 / 3), 0, 0, 250, 175, 250, 175);

        // draw fomx
        /*
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, FOMX);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.doBackgroundFade ? (float) MathHelper.ceil(MathHelper.clamp(f, 0.0F, 1.0F)) : 1.0F);
        */
        int fomxSize = 118;
        context.drawTexture(FOMX, (width / 2) - (fomxSize / 2), height / 2 - fomxSize + 32, 0, 0, 128, fomxSize, fomxSize, fomxSize, fomxSize);

        // draw texts
        int transparent = MathHelper.ceil(0.5f * 255.0F) << 24;

        // -> copyright
        context.drawText(this.textRenderer, mojangCopyrightText.getString(), this.mojangCopyrightTextX, this.height - 10, 16777215 | transparent, false);
        // -> copyright hover
        if (mouseX > this.mojangCopyrightTextX && mouseX < this.mojangCopyrightTextX + this.mojangCopyrightTextWidth && mouseY > this.height - 10 && mouseY < this.height) {
            context.fill(this.mojangCopyrightTextX, this.height - 1, this.mojangCopyrightTextX + this.mojangCopyrightTextWidth, this.height, 16777215 | transparent);
        }

        String gameVersion = "Minecraft " + SharedConstants.getGameVersion().getName();
        assert this.client != null;
        if (this.client.isDemo()) {
            gameVersion = gameVersion + " Demo";
        } else {
            gameVersion = gameVersion + ("release".equalsIgnoreCase(this.client.getVersionType()) ? "" : "/" + this.client.getVersionType());
        }

        context.drawTextWithShadow(this.textRenderer, gameVersion, 4, this.height - 10, 16777215 | transparent);
        context.drawTextWithShadow(this.textRenderer, "FoxClient " + Main.SIMPLE_VERSION, 4, this.height - 20, 16777215 | transparent);

        int foxClientTextWidth = this.textRenderer.getWidth("FoxClient " + Main.SIMPLE_VERSION);
        if (mouseX > 2 && mouseX < 2 + foxClientTextWidth && mouseY > this.height - 24 && mouseY < this.height - 10) {
            context.drawTooltip(this.textRenderer, Text.of("FoxClient " + Main.VERSION), mouseX, mouseY);
        }
        context.drawTextWithShadow(this.textRenderer, foxclientCopyrightText, this.foxclientCopyrightTextX, this.height - 20, 16777215 | transparent);

        super.render(context, mouseX, mouseY, delta);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        if (mouseX > (double) this.mojangCopyrightTextX && mouseX < (double) (this.mojangCopyrightTextX + this.mojangCopyrightTextWidth) && mouseY > (double) (this.height - 10) && mouseY < (double) this.height) {
            assert this.client != null;
            this.client.setScreen(new CreditsScreen(false, () -> {
                this.client.setScreen(TitleScreen.this);
            }));
        }
        return false;
    }

    void loadMiniButtons() {
        int spacingY = 26;
        int y = this.height / 2 + 10;
        int center = (this.width / 2) - 10;

        assert this.client != null;
        for (int i = 0; i < 7; ++i) {
            Identifier tex = EMPTY_BUTTON;
            ButtonWidget.PressAction pressAction = null;
            int x = center;
            String tooltip = "";

            switch (i + 1) {
                case 1 -> {
                    tex = DISCORD_BUTTON;
                    pressAction = (button) -> Util.getOperatingSystem().open(Main.DISCORD_INVITE);
                    x -= 30 * 3;
                    tooltip = "Join our Discord";
                }
                case 2 -> {
                    tex = ACCESSIBILITY_BUTTON;
                    pressAction = (button) -> this.client.setScreen(new AccessibilityOptionsScreen(this, this.client.options));
                    x -= 30 * 2;
                    tooltip = "Accessibility Options";
                }
                case 3 -> {
                    tex = MODS_BUTTON;
                    pressAction = (button) -> {
                        if (FabricLoader.getInstance().isModLoaded("modmenu")) {
                            try {
                                Class<?> modMenuGui = Class.forName("com.terraformersmc.modmenu.gui.ModsScreen");

                                this.client.setScreen((Screen) modMenuGui.getDeclaredConstructor(Screen.class).newInstance(this));
                            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                                     ClassNotFoundException | InstantiationException e) {
                                e.printStackTrace();
                            }
                        } else {
                            FoxClientToast toaster = MinecraftClient.getInstance().getToastManager().getToast(FoxClientToast.class, Toast.TYPE);
                            if (toaster == null) {
                                MinecraftClient.getInstance().getToastManager().add(new FoxClientToast(
                                        Text.of("FoxClient"), TextUtils.translatable("foxclient.gui.toast.modmenu.missing"), 10000L));
                            }
                        }
                    };
                    x -= 30;
                    tooltip = "Mods";
                }
                case 4 -> {
                    tex = REALMS_BUTTON;
                    pressAction = (button) -> this.client.setScreen(new RealmsMainScreen(this));
                    tooltip = "Realms";
                }
                case 5 -> {
                    tex = FOXCLIENT_OPTIONS_BUTTON;
                    pressAction = (button) -> this.client.setScreen(new FoxClientSettingsScreen(true));
                    x += 30;
                    tooltip = "FoxClient Options";
                }
                case 6 -> {
                    tex = OPTIONS_BUTTON;
                    pressAction = (button) -> this.client.setScreen(new OptionsScreen(this, this.client.options));
                    x += 30 * 2;
                    tooltip = "Minecraft Options";
                }
                case 7 -> {
                    tex = EXIT_BUTTON;
                    pressAction = (button) -> this.client.scheduleStop();
                    x += 30 * 3;
                    tooltip = "Exit";
                }
            }

            // TODO: mojang implemented 9-slicing for buttons, we gotta switch to that
            // this is a temporary solution
            ButtonTextures textures = new ButtonTextures(tex, tex);
            this.addDrawableChild(new FoxClientMiniButton(x, y + spacingY * 2, 20, 20, textures, pressAction, TextUtils.translatable(""), TextUtils.string(tooltip)));
        }
    }
}
