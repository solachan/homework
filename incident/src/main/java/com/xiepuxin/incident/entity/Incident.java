package com.xiepuxin.incident.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * (Incident)实体类
 *
 * @author makejava
 * @since 2024-12-14 23:44:03
 */
@Entity
@Table(name = "Incident",
        indexes = {
                @Index(name = "idx_type", columnList = "type"),
                @Index(name = "idx_type_title", columnList = "type, title"),
                @Index(name = "idx_time", columnList = "time")
        })
public class Incident implements Serializable {
    private static final long serialVersionUID = -39938881749828924L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String type;
    
    private String title;
    
    private String description;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    // Fingerprint field with unique constraint
    @Column(unique = true, nullable = false)
    private String fingerprint;

    public Incident() {
    }


    public Incident(Long id, String type, String title, String description, Integer status, Date time) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.status = status;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    /**
     * 计算指纹，相同指纹的事件看做是相同事件
     * 假设类型、标题、描述、状态均一致的事件为统一事件
     */
    // Method to generate fingerprint
    public String generateFingerprint() {
        try {
            String input = type + title + description + status;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

