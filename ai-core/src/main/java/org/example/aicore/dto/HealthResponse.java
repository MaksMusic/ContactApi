package org.example.aicore.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.aicore.enums.HealthStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthResponse {

    private HealthStatus status;
}
