#!/bin/bash

kotlinc *.kt
./halite -d "30 30" "kotlin MyBot" "kotlin RandomBot"
