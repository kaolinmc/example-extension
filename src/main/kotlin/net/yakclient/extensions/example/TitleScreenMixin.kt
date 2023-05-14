package net.yakclient.extensions.example


//@MixinSource("elr")
public abstract class TitleScreenMixin {
    private var r: String = ""

//    @Inject(
//        to = "b()V",
//        InjectionPoints.BeforeEnd::class
//    )
    public fun changeSplash() {
        r = "YakClients awesome"
    }
}