package com.hhub.bookhub.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("idMovie")
    @Expose
    private String idMovie;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("vote")
    @Expose
    private String vote;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("introduction")
    @Expose
    private String introduction;
    @SerializedName("story")
    @Expose
    private String story;
    @SerializedName("storyImage")
    @Expose
    private String storyImage;
    @SerializedName("character")
    @Expose
    private String character;
    @SerializedName("characterImage")
    @Expose
    private String characterImage;
    @SerializedName("art")
    @Expose
    private String art;
    @SerializedName("artImage")
    @Expose
    private String artImage;
    @SerializedName("feel")
    @Expose
    private String feel;
    @SerializedName("feelImage")
    @Expose
    private String feelImage;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("messageImage")
    @Expose
    private String messageImage;
    @SerializedName("end")
    @Expose
    private String end;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getStoryImage() {
        return storyImage;
    }

    public void setStoryImage(String storyImage) {
        this.storyImage = storyImage;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCharacterImage() {
        return characterImage;
    }

    public void setCharacterImage(String characterImage) {
        this.characterImage = characterImage;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getArtImage() {
        return artImage;
    }

    public void setArtImage(String artImage) {
        this.artImage = artImage;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public String getFeelImage() {
        return feelImage;
    }

    public void setFeelImage(String feelImage) {
        this.feelImage = feelImage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageImage() {
        return messageImage;
    }

    public void setMessageImage(String messageImage) {
        this.messageImage = messageImage;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
