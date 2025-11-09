#!/bin/bash

# --- Configuration ---
BASE_URL=${BASE_URL:-"http://localhost:8080"}
LOG_FILE="./load_simulation.log"

# Clear log file on startup
> "$LOG_FILE"

# Function to generate a random integer in a given range (inclusive)
# Usage: random_sleep MIN MAX
random_sleep() {
    MIN=$1
    MAX=$2
    # Calculate difference and use modulo arithmetic to get a value
    # between MIN and MAX (inclusive).
    echo $(( RANDOM % (MAX - MIN + 1) + MIN ))
}

# --- SCENARIO 1: Frequent Root Access (1.5 to 3 minutes) ---
access_root() {
    echo "Starting Root Access Loop (2-3 min intervals)..." >> "$LOG_FILE"
    while true; do
        # 1. Access the endpoint
        RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/")
        TIMESTAMP=$(date +"%Y-%m-%d %H:%M:%S")
        echo "$TIMESTAMP [ROOT] Request sent to / - Status: $RESPONSE" >> "$LOG_FILE"

        # 2. Calculate random sleep time between 2 and 3 minutes (180s to 300s)
        SLEEP_SECONDS=$(random_sleep 120 180)
        
        echo "$TIMESTAMP [ROOT] Success. Sleeping for $SLEEP_SECONDS seconds (2-3 min)..." >> "$LOG_FILE"
        sleep "$SLEEP_SECONDS"
    done
}

# --- SCENARIO 2: Infrequent Error Trigger (20 to 30 minutes) ---
trigger_error() {
    echo "Starting Error Trigger Loop (20-30 min intervals)..." >> "$LOG_FILE"
    while true; do
        # 1. Access the endpoint (expecting a 500 status code)
        RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/error/runtime")
        TIMESTAMP=$(date +"%Y-%m-%d %H:%M:%S")
        echo "$TIMESTAMP [ERROR] Request sent to /error/runtime - Status: $RESPONSE" >> "$LOG_FILE"

        # 2. Calculate random sleep time between 20 and 30 minutes (1200s to 1800s)
        SLEEP_SECONDS=$(random_sleep 1200 1800)

        echo "$TIMESTAMP [ERROR] Runtime simulated. Sleeping for $SLEEP_SECONDS seconds (20-30 min)..." >> "$LOG_FILE"
        sleep "$SLEEP_SECONDS"
    done
}

# --- SCENARIO 3: 30s Delay Trigger (45 to 90 minutes) ---
trigger_delay() {
    echo "Starting 30s Delay Trigger Loop (45-90 min intervals)..." >> "$LOG_FILE"
    while true; do
        # 1. Access the endpoint (expecting 30s delay in response)
        RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/error/delay")
        TIMESTAMP=$(date +"%Y-%m-%d %H:%M:%S")
        echo "$TIMESTAMP [ERROR] Request sent to /error/delay - Status: $RESPONSE" >> "$LOG_FILE"

        # 2. Calculate random sleep time between 45 and 90 minutes 
        SLEEP_SECONDS=$(random_sleep 2700 5400)

        echo "$TIMESTAMP [ERROR] Runtime 30s Delay simulated. Sleeping for $SLEEP_SECONDS seconds (45-90 min)..." >> "$LOG_FILE"
        sleep "$SLEEP_SECONDS"
    done
}

# --- Main Execution ---

echo "Starting load simulation at $(date) (Check load_simulation.log for details)"

# Start all functions in the background
access_root &
PID_ROOT=$!

access_root &
PID_ROOT=$!

trigger_error &
PID_ERROR=$!

trigger_delay &
PID_DELAY=$!

# Add a trap to stop the background jobs when the script is stopped
trap "kill $PID_ROOT $PID_ERROR $PID_DELAY; echo 'Simulation stopped.'; exit" EXIT

# Keep the main script running until stopped by the user (Ctrl+C)
wait