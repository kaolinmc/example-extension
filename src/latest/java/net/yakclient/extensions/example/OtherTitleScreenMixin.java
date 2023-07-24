package net.yakclient.extensions.example;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.Bootstrap;
import net.yakclient.client.api.InjectionPoints;
import net.yakclient.client.api.annotation.Mixin;
import net.yakclient.client.api.annotation.SourceInjection;

import java.util.List;

@Mixin("net.minecraft.client.gui.screens.TitleScreen")
public abstract class OtherTitleScreenMixin extends Screen {
    protected OtherTitleScreenMixin(Component $$0) {
        super($$0);
    }

    @SourceInjection(
            point = InjectionPoints.AFTER_BEGIN,
            from = "net.yakclient.extensions.example.OtherTitleScreenMixin",
            to = "net.minecraft.client.gui.screens.TitleScreen",
            methodFrom = "initV2(II)V",
            methodTo = "createNormalMenuOptions(II)V",
            priority = 0
    )
    void initV2(int f, int b) {
        Bootstrap.realStdoutPrintln("ERERE!!");

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        Runnable a = () -> {
//            var button = Button.builder(Component.literal("Some value here, idk dude"), (it) -> {
//
//            }).bounds(width / 2 - 100, f, 200, 20).build();
        };

        a.run();
//        this.addRenderableWidget(button)
    }
}
