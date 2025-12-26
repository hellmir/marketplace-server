//package com.personal.shop.user.port.in.mapper;
//
//import com.greenbowl.greenbowlserver.recipe.domain.Nutrition;
//import com.greenbowl.greenbowlserver.recipe.domain.Recipe;
//import com.greenbowl.greenbowlserver.recipe.domain.RecipeIngredient;
//import com.greenbowl.greenbowlserver.recipe.port.in.command.CreateDetailedRecipeCommand;
//import com.greenbowl.greenbowlserver.recipe.port.in.command.CreateRecipeCommand;
//import com.greenbowl.greenbowlserver.recipe.port.in.command.NutritionCommand;
//
//import java.util.stream.Collectors;
//
//public class RecipeCommandToDomainMapper {
//    public static Recipe mapToDomainEntity(CreateRecipeCommand createRecipeCommand) {
//        return Recipe.of(
//                createRecipeCommand.getUserId(),
//                createRecipeCommand.getName(),
//                createRecipeCommand.getImageUrl(),
//                createRecipeCommand.getCookingTime(),
//                createRecipeCommand.getCalories()
//        );
//    }
//
//    public static Recipe mapToDomainEntity(CreateDetailedRecipeCommand createDetailedRecipeCommand) {
//        return Recipe.of(
//                createDetailedRecipeCommand.getUserId(),
//                createDetailedRecipeCommand.getName(),
//                createDetailedRecipeCommand.getImageUrl(),
//                createDetailedRecipeCommand.getCookingTime(),
//                createDetailedRecipeCommand.getCalories(),
//                createDetailedRecipeCommand.getOneLineIntroduction(),
//                createDetailedRecipeCommand.getIngredients()
//                        .stream().
//                        map(ingredient -> RecipeIngredient.of(
//                                ingredient.getName(), ingredient.getWeight())
//                        )
//                        .collect(Collectors.toList()),
//                createDetailedRecipeCommand.getIntroduction(),
//                mapToDomain(createDetailedRecipeCommand.getNutrition())
//        );
//    }
//
//    public static Nutrition mapToDomain(NutritionCommand nutritionCommand) {
//        return Nutrition.of(
//                nutritionCommand.getCarbohydrate(),
//                nutritionCommand.getProtein(),
//                nutritionCommand.getFat(),
//                nutritionCommand.getSodium(),
//                nutritionCommand.getSugar()
//        );
//    }
//}
