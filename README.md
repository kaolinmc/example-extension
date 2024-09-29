<a id="readme-top"></a>
## Welcome to the ExtensionFramework Example 
[![Latest Stable Version](https://img.shields.io/github/v/release/extframework/example-extension?include_prereleases)](https://github.com/extframework/example-extension)
[![Top Language](https://img.shields.io/github/languages/top/extframework/example-extension)](https://github.com/extframework/example-extension)
[![Last Commit](https://img.shields.io/github/last-commit/extframework/example-extension)](https://github.com/extframework/example-extension)
[![Issues Open](https://img.shields.io/github/issues/extframework/example-extension)](https://github.com/extframework/example-extension)

[![Supported ](https://img.shields.io/badge/Mac-Supported-Green)](https://github.com/extframework/example-extension)
[![Supported ](https://img.shields.io/badge/Windows-BUG-ff0000)](https://github.com/extframework/example-extension)
[![Supported ](https://img.shields.io/badge/Linux-Unknown-aaaaaa)](https://github.com/extframework/example-extension)

This example will get you up and running a simple extension in Minecraft! Then, show you where and how to use the system
to create your own extensions!

## Getting Started

#### Prerequisites
You need the following on your machine.  Standard gradle development stuff for java:
- *at least* Java 21 (JDK) installed and JAVA_HOME set to the jdk directory
- gradle
- git

#### Set up
- clone this repository somewhere locally
- run gradlew launch

That's it!  The system will download all the required libraries and then launch Minecraft.  You'll know
  if it was completely successful because the Minecraft launch will display "YakClient is awsome"


## Understanding Extension structures
One of the most important things to understand is the directory structure
### Directory layout
Under the 'src' directory you can have multiple directories **which can be mapped to different MC versions!**  This is
one of five main strengths of the Yak system.
* **src/main** (the standard maven directory layout): contains all common code (though NO MC-specific code can go here)
* **src/&lt;whatever you like&gt;** a project that will be mapped in the build.gradle to a specific minecraft version

Any version partition can call code in the src/main partition but main cannot see anything in the version partitions. 
Also, again, note that we've restricted any MC-code from the main to insure good design and reduce code-version fragility.

At the simplest, you can develop almost everything in the version partition, the only thing necessary in src/main 
is the Extension class.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

##### Minecraft version mapping

The entry point for an extension is defined in the build.gradle file but looking at ExampleExtension in main/kotlin/...
extends Extension abstract class:

```
public abstract class Extension public constructor() {
  public abstract fun init(): kotlin.Unit
}
```
and thus implements init():
```
    override fun init() {
        asdf(5)
        println("Ok inniting here")
        onBootStrap {
            println("Boot strapping")
            registerBlock()
        }
    }
```
This has two interesting features, the onBootStrap callback and the registerBlock() function. onBootStrap is defined locally
in Utils.kt where it is just added to a global list but doesn't really do anything.  The trick is that it is picked up in 
the version partition class BootstrapCallbackMixin:

```
@Mixin(BuiltInRegistries::class)
object BootstrapCallbackMixin {
    @JvmStatic
    @SourceInjection(point = "after-begin", methodTo = "freeze()V", priority = 0)
    fun overwriteFreeze(continuation: InjectionContinuation) : InjectionContinuation.Result {
        for (bootstrapCallback in bootstrapCallbacks) {
            bootstrapCallback.invoke()
        }
        return continuation.resume()
    }
}
```
There's a good deal to understand here but we'll save it for the Mixins section below.

Back at the init call, if you go into the registerBlock() call, you'll see it does just this:
```
@Feature
fun registerBlock(): Unit = throw FeatureImplementationException()
```
It uses annotations to let the system know this is a Feature but .... again, what?  It doesn't do anything.  
Well, worse, it throws an error?  Yes, this also, will be picked up in the version
partition, overwritten and implemented.  This necessitates a discussion of ....

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Features
Features are effectively interfaces, anotated with @Feature at the beginning of the function, 
allowing each feature partition to implement the feature-specific version of this code.
If you look at where this is implemented, in createBlock in the version partition

```
@Feature
fun registerBlock() {
    println("Hey um")
    val exampleBlock = MyCustomBlock(
        BlockBehaviour.Properties.of(),
    ) ... code continues
```
And you'll see here a bunch of actual MC code for adding in a new block.  We're hoping you're already good with
that side of Minecraft development. But note, that code has changed a bunch over the versions, so make sure you're
using the correct classes for the version you're targeting!

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Mixins
In general, mixins are any time code is "mixed into" other code.  And if you've done MC dev you're familiar with these.
You need to tell *where* you want to put the code (at the beginning of the function, at the end, etc.)

Here's a great example, which adds some text to the launcher main screen!  the @Mixin(<class>) annotation tells you which
MC class you're targeting.  This allows you to go crazy on the MC source!  
```
@Mixin(TitleScreen::class)
abstract class TitleScreenMixin(ignored: Component) : Screen(ignored) {
private var splash: SplashRenderer? = null
    @SourceInjection(
            point = "before-end",
            methodTo = "init()V",
            priority = 0
    )
    fun initV1(continuation: InjectionContinuation) : InjectionContinuation.Result {
        splash = SplashRenderer("Yakclient is awesome!")
        Bootstrap.realStdoutPrintln("Hey man")
        this.addRenderableWidget(
                createTheWidget(width, height)
        )
        return continuation.resume()
    }
}
```
The most important annotation after @Mixin is the @SourceInjection, which tells you *where* you're putting the code.
Specifically @SourceInjection denotes where the given instructions are going in the target method in the target class. 
With point you can define what part of the method this targets.

There are three types of mixins supported, SourceInjection: which adds to an existing function, 
MethodInjection: which replaces an existing function and FieldInjection: adds a new field to a class.  More documentation 
all the possible values for these is provided in the ExtensionLoader project.

<!-- tweaker --> 
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Documentation 
Further documention can be found at:

* Mixins in the Extension Loader project [https://github.com/extframework/ext-loader](https://github.com/extframework/ext-loader)
* Gradle plugin options [https://github.com/extframework/extframework-gradle-plugin](https://github.com/extframework/extframework-gradle-plugin)
<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Contact 

<ul>
  <li> Discord: @durganmcbroom  or  <a href="https://discord.gg/3fP4N27JPH">@extframework discord</a></li>
  <li> Linkedin: https://www.linkedin.com/in/durganmcbroom/ </li> 
  <li> Email: durganmcbroom@gmail.com </li>
</ul>
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CREDITS -->
## Credits

Durgan McBroom

[![GitHub Badge](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/durganmcbroom)
[![LinkedIn Badge](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/durganmcbroom/)
<p align="right">(<a href="#readme-top">back to top</a>)</p>