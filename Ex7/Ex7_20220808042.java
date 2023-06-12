import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.*;
interface Damageable{
    void takeDamage(int damage);
    void takeHealing(int healing);
    boolean isAlive();
}
interface Caster{
    void castSpell(Damageable target);
    void learnSpell(Spell spell);
}
interface Combat extends Damageable{
    void attack(Damageable target);
    void lootWeapon(Weapon weapon);
}
interface Useable{
    int use();
}
class Spell implements Useable{
    private int minHealt;
    private int maxHealt;
    private String name;

    Spell(int minHealt, int maxHealt, String name) {
        this.minHealt = minHealt;
        this.maxHealt = maxHealt;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    private int heal(){
        return (int)(Math.random() * (maxHealt - minHealt + 1) + minHealt);
    }
    public int use(){
        return heal();
    }
}
class Weapon implements Useable{
    private int minDamage;
    private int maxDamage;
    private String name;

    Weapon(int minDamage, int maxDamage, String name) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.name = name;
    }
    private int attack(){
        return (int)(Math.random() * (maxDamage - minDamage + 1) + minDamage);
    }
    public int use(){
        return attack();
    }

}
class Attributes{
    private int strength;
    private int intelligence;

    Attributes(){
        strength = 3;
        intelligence = 3;
    }
    Attributes(int strength, int intelligence){
        this.strength = strength;
        this.intelligence = intelligence;
    }
    public void increaseStrength(int amount){
        strength += amount;
    }
    public void increaseIntelligence(int amount){
        intelligence += amount;
    }
    public int getIntelligence() {
        return intelligence;
    }
    public int getStrength() {
        return strength;
    }
    @Override
    public String toString(){
        return "Attributes [Strength= " + strength + ", Intelligence= " + intelligence + "]";
    }
}
abstract class Character implements Comparable{
    private String name;
    protected int level;
    protected Attributes attributes;
    protected int health;

    Character(String name, Attributes attributes) {
        this.name = name;
        this.attributes = attributes;
    }
    public String getName() {
        return name;
    }
    public int getLevel() {
        return level;
    }
    abstract public void levelUp();
    public int getStrength(){
        return attributes.getStrength();
    }
    @Override
    public String toString(){
        return getClass().getSimpleName() + "LvL: " + level + " - " + attributes;
    }
    @Override
    public int compareTo(Object o) {
        return Integer.compare(level, ((Character) o).getLevel());
    }
}
class PlayableCharacter extends Character implements Damageable{
    private boolean inParty;
    private Party party;

    PlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
    }
    public boolean isInParty(){
        return inParty;
    }
    public void joinParty(Party party)throws PartyLimitReachedException, AlreadyInPartyException{
        try{
            if(!isInParty()){
                party.addCharacter(this);
                this.party = party;
                inParty = true;
            }else {
                throw new AlreadyInPartyException("Character is already in party");
            }
        }
        catch(PartyLimitReachedException e){
            System.out.println(e.getMessage());
        }
        catch(AlreadyInPartyException e){
            System.out.println(e.getMessage());
        }
    }
    public void quitParty(){
        try{
            if(isInParty()){
                party.removeCharacter(this);
                this.party = null;
                inParty = false;
            }else {
                throw new CharacterIsNotInPartyException("Character is not in party");
            }
        }
        catch(CharacterIsNotInPartyException e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void takeDamage(int damage) {
        health = health - damage;
    }
    @Override
    public void takeHealing(int healing) {
        health = health + healing;
    }
    @Override
    public boolean isAlive() {
        if (health > 0) {
            return true;
        }else {
            return false;
        }
    }
    @Override
    public int compareTo(Object o) {
        return Integer.compare(level, ((Character) o).getLevel());
    }
    @Override
    public void levelUp() {
        level++;
    }
}
abstract class NonPlayableCharacter extends Character{
    NonPlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
    }
}
class Merchant extends NonPlayableCharacter{
    
    Merchant(String name) {
        super(name, new Attributes(0, 0));
    }
    @Override
    public void levelUp() {
        level++;
    }  
    @Override 
    public int compareTo(Object o) {
        return Integer.compare(level, ((Character) o).getLevel());
    }
}
class Skeleton extends NonPlayableCharacter implements Damageable{
    
    Skeleton(String name, Attributes attributes) {
        super(name, attributes);
        
    }
    @Override
    public void takeDamage(int damage) {
        health = health - damage;
    }
    public void lootWeapon(Weapon weapon){
        
    }
    @Override
    public void takeHealing(int healing) {
        health = health - healing;
    }
    @Override
    public boolean isAlive() {
        if (health > 0) {
            return true;
        }else {
            return false;
        }
    }
    @Override
    public void levelUp() {
        increaseStrength(1);
        increaseIntelligence(1);
        level++;
    }
}
class Warrior extends PlayableCharacter implements Combat{
    Warrior(String name) {
        super(name, new Attributes(4, 2));
        health = 35;
    }
    @Override
    public void takeDamage(int damage) {
        health = health - damage;
    }
    @Override
    public void takeHealing(int healing) {
        health = health + healing;
    }
    @Override
    public boolean isAlive() {
        if (health > 0) {
            return true;
        }else {
            return false;
        }
    }
    @Override
    public int compareTo(Object o) {
        return Integer.compare(level, ((Character) o).getLevel());
    }
    @Override
    public void levelUp() {
        attributes.increaseStrength(2);
        attributes.increaseIntelligence(1);
        level++;
    }
    @Override
    public void attack(Damageable target) {
        target.takeDamage(getStrength());
    }
    @Override
    public void lootWeapon(Weapon weapon){

    }
}
class Cleric extends PlayableCharacter implements Caster{
    Cleric(String name) {
        super(name, new Attributes(2, 4));
        health = 25;
    }
    @Override
    public void takeDamage(int damage) {
        health = health - damage;
    }
    @Override
    public void takeHealing(int healing) {
        health = health + healing;
    }
    @Override
    public boolean isAlive() {
        if (health > 0) {
            return true;
        }else {
            return false;
        }
    }
    @Override
    public int compareTo(Object o) {
        return Integer.compare(level, ((Character) o).getLevel());
    }
    @Override
    public void levelUp() {
        attributes.increaseStrength(1);
        attributes.increaseIntelligence(2);
        level++;
    }
    @Override
    public void castSpell(Damageable target){
        target.takeDamage(attributes.getIntelligence());
    }
    @Override 
    public void learnSpell(Spell spell){
        System.out.println("Cleric learned spell: " + spell.getName());
    }
}
class Paladin extends PlayableCharacter implements Combat{
    public Paladin(String name) {
        super(name, new Attributes(3, 3));
        health = 30;
    }
    @Override
    public void levelUp(){
        attributes.increaseStrength(2);
        attributes.increaseIntelligence(2);
        level++;
    }
    @Override
    public void takeDamage(int damage) {
        health = health - damage;
    }
    @Override
    public void takeHealing(int healing) {
        health = health + healing;
    }
    @Override
    public boolean isAlive() {
        if (health > 0) {
            return true;
        }else {
            return false;
        }
    }
    @Override
    public int compareTo(Object o) {
        return Integer.compare(level, ((Character) o).getLevel());
    }
    @Override
    public void attack(Damageable target) {
        target.takeDamage(getStrength());
    }
    @Override
    public void lootWeapon(Weapon weapon){

  }
}
class Party{
    private final int partyLimit = 8;
    private List<Combat> fighters;
    private List<Caster> healers; 
    private List<Paladin> paladins;
    private int mixedCount;

    Party(){
        fighters = new ArrayList<>();
        healers = new ArrayList<>();
        paladins = new ArrayList<>();
        mixedCount = 0;
    }
    public void addCharacter(PlayableCharacter character)throws PartyLimitReachedException, AlreadyInPartyException{
        if (fighters.size() + healers.size() + paladins.size() >= partyLimit){
            throw new PartyLimitReachedException("Party limit reached");
        }
        if (character.isInParty()){
            throw new AlreadyInPartyException("Character is already in party");
        }
        if (character instanceof Warrior){
            fighters.add((Combat) character);
        }else if (character instanceof Cleric){
            healers.add((Caster) character);
        }else if (character instanceof Paladin){
            paladins.add((Paladin) character);
            mixedCount++;
        }
    }
    public void removeCharacter(PlayableCharacter character)throws CharacterIsNotInPartyException{
        if (!character.isInParty()){
            throw new CharacterIsNotInPartyException("Character is not in party");
        }
        if (character instanceof Warrior){
            fighters.remove((Combat) character);
        }else if (character instanceof Cleric){
            healers.remove((Caster) character);
        }else if (character instanceof Paladin){
            paladins.remove((Paladin) character);
            mixedCount--;
        }
    }
    public void partyLevelUp(){
        for (Combat fighter : fighters){
            ((Character) fighter).levelUp();
        }
        for (Caster healer : healers){
            ((Character) healer).levelUp();
        }
        for (Paladin paladin : paladins){
            paladin.levelUp();
        }
    }
    @Override
    public String toString() {
        List<PlayableCharacter> allCharacters = new ArrayList<>();
        for (Combat fighter : fighters) {
            allCharacters.add((PlayableCharacter) fighter);
        }
        for (Caster healer : healers) {
            allCharacters.add((PlayableCharacter) healer);
        }
        for (Paladin paladin : paladins) {
            allCharacters.add((PlayableCharacter) paladin);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Party Members (sorted by level):\n");
    
        allCharacters.sort(Comparator.comparingInt(PlayableCharacter::getLevel)); // Sort by level
    
        for (PlayableCharacter character : allCharacters) {
            sb.append(character.getName()).append(" (Level ").append(character.getLevel()).append(")\n");
        }
        return sb.toString();
    }
}
class Barrel implements Damageable{
    private int health = 30;
    private int capacity = 10;

    public void explode(){
        System.out.println("Expodes");
    }
    public void repair(){
        System.out.println("Repairing");
    }
    @Override
    public void takeDamage(int damage){
        health = health - damage;
        if (health <= 0){
            explode();
        }
    }
    @Override
    public void takeHealing(int healing){
        health = health + healing;
        repair();
        if (health > capacity){
            health = capacity;
        }
    }
    @Override
    public boolean isAlive(){
        if (health > 0){
            return true;
        }else {
            return false;
        }
    }
}
class TrainingDummy implements Damageable{
    private int health = 25;

    @Override
    public void takeDamage(int damage){
        health = health - damage;
    }
    @Override
    public void takeHealing(int healing){
        health = health + healing;
        repair();
        if (health > capacity){
            health = capacity;
        }
    }
    @Override
    public boolean isAlive(){
        if (health > 0){
            return true;
        }else {
            return false;
        }
    }
    public void repair(){
        System.out.println("Repairing");
    }


}
class PartyLimitReachedException extends Exception{
    public PartyLimitReachedException(String message){
        super(message);
    }
}
class AlreadyInPartyException extends Exception{
    public AlreadyInPartyException(String message){
        super(message);
    }
}
class CharacterIsNotInPartyException extends Exception{
    public CharacterIsNotInPartyException(String message){
        super(message);
    }
}









