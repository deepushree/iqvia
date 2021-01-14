package com.jpaspecdomain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Member {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private String zipCode;
    private String interests;
    private boolean active;

    @JoinTable(name = "Member_Class_Cross_Ref",
            joinColumns = @JoinColumn(
                    name = "member_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "class_id",
                    referencedColumnName = "id"
            ))
    @ManyToMany
    private Set<MemberClass> memberClasses;

    public Member() { }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<MemberClass> getMemberClasses() {
		return memberClasses;
	}

	public void setMemberClasses(Set<MemberClass> memberClasses) {
		this.memberClasses = memberClasses;
	}
    
    
}
