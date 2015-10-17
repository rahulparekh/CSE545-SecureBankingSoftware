package com.sbs.group11.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

// TODO: Auto-generated Javadoc
/**
 * The Class ExternalUser - Hibernate model for External Users.
 *
 * @author Rahul
 */

@Entity
@Table(name="ExternalUser")
public class ExternalUser {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/** The first name. */
	@NotNull
	@Size(min=2, max=35)
    @Column(name = "FirstName", nullable = false, length=35)
    private String firstName;
	
	/** The middle name. */
	@Size(min=3, max=35)
    @Column(name = "MiddleName", nullable = false, length=35)
    private String middleName;
	
	/** The last name. */
	@NotNull
	@Size(min=3, max=70)
    @Column(name = "LastName", nullable = false, length=70)
    private String lastName;
    
    /** The created at. */
    @NotNull 
    @Column(name = "CreatedAt", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime createdAt;
    
    /** The last login at. */
    @NotNull 
    @Column(name = "LastLoginAt", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime lastLoginAt;
    
    /** The updated at. */
    @NotNull 
    @Column(name = "UpdatedAt", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime updatedAt;
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }
 
    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }
 
    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }
 
    /**
     * Sets the first name.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Gets the middle name.
     *
     * @return the middle name
     */
    public String getMiddleName() {
        return middleName;
    }
 
    /**
     * Sets the middle name.
     *
     * @param middleName the new middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
 
    /**
     * Gets the created at.
     *
     * @return the created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
 
    /**
     * Sets the created at.
     *
     * @param createdAt the new created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Gets the last login at.
     *
     * @return the last login at
     */
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }
 
    /**
     * Sets the last login at.
     *
     * @param lastLoginAt the new last login at
     */
    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.createdAt = lastLoginAt;
    }
    
    /**
     * Gets the updated at.
     *
     * @return the updated at
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
 
    /**
     * Sets the updated at.
     *
     * @param updatedAt the new updated at
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
 
	
}
