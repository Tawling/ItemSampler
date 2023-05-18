package kaptainwutax.itemsampler.client;

import kaptainwutax.itemsampler.ItemSampler;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ItemSampler.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientTicker {

    public static int TICK = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            TICK++;
        }
    }

}
