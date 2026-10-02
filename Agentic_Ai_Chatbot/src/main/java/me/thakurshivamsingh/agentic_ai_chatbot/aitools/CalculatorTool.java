package me.thakurshivamsingh.agentic_ai_chatbot.aitools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;


@Component
public class CalculatorTool {

    @Tool(description = """
    Use it to perform mathematical operations.
    Supported operations are => add, multiply, subtract, divide, amd power.""")
    public double calculate(
            @ToolParam(description = "Operation are , add , subtract , multiply , divide and power")
            String operation ,

            @ToolParam(description = "First Number")
            double a ,

            @ToolParam(description = "Second number")
            double b) {

        System.out.println("CalculatorTool tool called");
        if(operation.equals("add")){
            return a+b;
        }
        else if(operation.equals("subtract")){
            return a-b;
        }else if(operation.equals("multiply")){
            return a*b;
        }else  if(operation.equals("divide")) {
            if(b==0) {
                throw new IllegalArgumentException("Divide by zero");
            }else{
                return a/b;
            }
        } else if(operation.equals("power")){
            return Math.pow(a,b);
        }

        throw new IllegalArgumentException("Invalid operation: " + operation);
    }
}
