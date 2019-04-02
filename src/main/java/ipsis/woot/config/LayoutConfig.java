package ipsis.woot.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class LayoutConfig {

    public static ForgeConfigSpec.BooleanValue TEXTURED_RENDER;
    public static ForgeConfigSpec.DoubleValue LAYOUT_OPACITY;
    public static ForgeConfigSpec.DoubleValue LAYOUT_SIZE;

    public static void init(ForgeConfigSpec.Builder serverBuilder, ForgeConfigSpec.Builder clientBuilder) {

        clientBuilder.push("layout");

        TEXTURED_RENDER = clientBuilder
                .comment("Render the factory layout with textures")
                .define("texturedRender", true);

        LAYOUT_OPACITY = clientBuilder
                .comment("Opacity of the factory layout block")
                .defineInRange("opacity", 0.95D,0.3D, 1.0D);

        LAYOUT_SIZE = clientBuilder
                .comment("Size of a factory layout rendered block")
                .defineInRange("size", 0.45D, 0.1D, 1.0D);

        clientBuilder.pop();
    }
}