package eu.pollux28.skis.setup;

import eu.pollux28.skis.Skis;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags {
    public static final class Blocks{
        public static final ITag.INamedTag<Block> ORE_SILVER = forge("ore/silver");
        public static final ITag.INamedTag<Block> STORAGE_BLOCKS_SILVER = forge("storage_blocks/silver");



        private static ITag.INamedTag<Block> forge(String path){
            return BlockTags.bind(new ResourceLocation("forge",path).toString());
        }
        private static ITag.INamedTag<Block> mod(String path){
            return BlockTags.bind(new ResourceLocation(Skis.MOD_ID,path).toString());
        }
    }
    public static final class Items{
        public static final ITag.INamedTag<Item> ORES_SILVER = forge("ore/silver");
        public static final ITag.INamedTag<Item> STORAGE_BLOCKS_SILVER = forge("storage_blocks/silver");

        public static final ITag.INamedTag<Item> INGOTS_SILVER = forge("ingots/silver");
        private static ITag.INamedTag<Item> forge(String path){
            return ItemTags.bind(new ResourceLocation("forge",path).toString());
        }
        private static ITag.INamedTag<Item> mod(String path){
            return ItemTags.bind(new ResourceLocation(Skis.MOD_ID,path).toString());
        }
    }
}
