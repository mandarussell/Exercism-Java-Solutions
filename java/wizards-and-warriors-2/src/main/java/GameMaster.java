public class GameMaster {

    // Method to return description of a Character:
    public String describe(Character character) {
        return String.format(
                "You're a level %d %s with %d hit points.",
                character.getLevel(),
                character.getCharacterClass(),
                character.getHitPoints()
            );
    }

    // Method to return description of a Destination
    public String describe(Destination destination) {
        return String.format(
                "You've arrived at %s, which has %s inhabitants.",
                destination.getName(), 
                destination.getInhabitants()
            );
    }

    // Method to return description of a TravelMethod
    public String describe(TravelMethod travelMethod) {
        switch (travelMethod) {
            case WALKING:
                return "You're traveling to your destination by walking.";
            default:
                return "You're traveling to your destination on horseback.";
        }
    }

    // Method to return a description of a Character, Destination and TravelMethod
    public String describe(Character character, Destination destination, TravelMethod travelMethod) {
        return String.format(
                "%s %s %s", 
                this.describe(character),
                this.describe(travelMethod),
                this.describe(destination)                
            );
    }

    // Method to return a description of a Character and Destination
    public String describe(Character character, Destination destination) {
        return String.format(
                "%s %s %s", 
                this.describe(character),
                this.describe(TravelMethod.WALKING),
                this.describe(destination)                
            );
    }    
}
