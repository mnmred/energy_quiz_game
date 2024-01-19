package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

import java.util.Comparator;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Activity implements Comparable<Activity> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private Long energyConsumption;
    private String picturePath;

    /**
     * Constructor for an Activity.
     *
     * @param description The description of the activity.
     * @param energyConsumption - The energy consumption in watt-hours.
     * @param picturePath - The path to the image file of the activity.
     */
    public Activity(String description, Long energyConsumption, String picturePath) {
        this.description = description;
        this.energyConsumption = energyConsumption;
        this.picturePath = picturePath;
    }

    /**
     * Constructor for object mappers.
     */
    @SuppressWarnings("unused")
    public Activity() {
        // for object mapper
    }


    /**
     * Compare whether two instances of a Activity are equal.
     *
     * All fields have to be equal for equality.
     * @param obj The object to be compared with.
     * @return Whether the two objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Get a hash code of the Activity instance.
     *
     * @return The hash code of the activity.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Get a string representation of the Activity instance.
     *
     * It is given in a multiline format.
     * @return The string representation of the activity.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

    /**
     * Getter for activity id.
     *
     * @return The activity id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for activity id.
     *
     * @param id The id to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for activity description.
     *
     * @return The activity description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for activity description.
     *
     * @param description The activity description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for energy consumption of the activity.
     *
     * @return The energy consumption in watt-hours.
     */
    public Long getEnergyConsumption() {
        return energyConsumption;
    }

    /**
     * Setter for energy consumption of the activity.
     *
     * @param energyConsumption The energy consumption value in watt-hours to be set.
     */
    public void setEnergyConsumption(Long energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    /**
     * Getter for the path of the picture of the activity.
     *
     * @return The file path to the picture.
     */
    public String getPicturePath() {
        return picturePath;
    }

    /**
     * Setter for the path of the picture of the activity.
     *
     * @param picturePath The path to the picture to be set.
     */
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public int compareTo(Activity o) {
        return Comparators.ENERGY.compare(this, o);
    }

    //class used by the Collections.sort of PreGameController line 103
    //to be able to sort the list of activities according to their
    //energy consumptions
    public static class Comparators {

        public static Comparator<Activity> ENERGY = new Comparator<Activity>() {
            @Override
            public int compare(Activity a1, Activity a2) {
                return a1.getEnergyConsumption().compareTo(a2.getEnergyConsumption());
            }
        };
    }
}
