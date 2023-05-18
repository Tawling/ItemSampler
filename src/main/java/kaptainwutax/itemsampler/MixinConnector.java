package kaptainwutax.itemsampler;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {

    @Override
    public void connect() {
        Mixins.addConfigurations("assets/" + ItemSampler.MOD_ID + "/" + ItemSampler.MOD_ID + ".mixins.json");
    }

}
