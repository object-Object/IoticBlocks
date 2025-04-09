package gay.object.ioticblocks.forge.datagen

import at.petrak.hexcasting.api.mod.HexTags
import at.petrak.paucal.api.datagen.PaucalRecipeProvider
import gay.object.ioticblocks.IoticBlocks
import gay.object.ioticblocks.registry.IoticBlocksItems
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Items
import java.util.function.Consumer

// we use Paucal's recipe provider as a base because it has a bunch of helpful stuff
class IoticBlocksRecipes(output: PackOutput) : PaucalRecipeProvider(output, IoticBlocks.MODID) {
    override fun buildRecipes(writer: Consumer<FinishedRecipe>) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IoticBlocksItems.DUMMY_ITEM.value)
            .define('S', Items.STICK)
            .define('A', Blocks.AMETHYST_BLOCK)
            .pattern("  A")
            .pattern(" S ")
            .pattern("S  ")
            .unlockedBy("has_item", hasItem(HexTags.Items.STAVES))
            .save(writer)
    }
}
