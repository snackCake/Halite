#!/bin/bash

kotlinc MyBot.kt
kotlinc RandomBot.kt
./halite -d "30 30" "kotlin MyBot" "kotlin RandomBot"
