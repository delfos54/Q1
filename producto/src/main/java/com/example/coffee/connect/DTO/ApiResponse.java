package com.example.coffee.connect.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <t>{
    private boolean respuesta ;
    private String mensaje;
    private t data; 



}
