#!/bin/bash

# Check if the correct number of arguments is provided
if [ "$#" -ne 2 ]; then
  echo "Usage: $0 print3digits.asm printfun.asm"
  exit 1
fi

# Assign arguments to variables
task2_asm="$1"
task3_asm="$2"

# Student's full name and ID
student_name="Your Full Name" # Replace with your full name
student_id="676669"           # Replace with your student ID

# Print the student's name and ID
echo "The name and studentID: $student_name, $student_id"

# Compile and execute Task 2
echo "Compiling $task2_asm"
nasm -f elf -F dwarf -g "$task2_asm" -o "${task2_asm%.asm}.o"
ld -m elf_i386 -o "${task2_asm%.asm}" "${task2_asm%.asm}.o"

echo "Executing $task2_asm"
./"${task2_asm%.asm}"

# Compile and execute Task 3
echo "Compiling $task3_asm"
nasm -f elf -F dwarf -g "$task3_asm" -o "${task3_asm%.asm}.o"
ld -m elf_i386 -o "${task3_asm%.asm}" "${task3_asm%.asm}.o"

echo "Executing $task3_asm"
./"${task3_asm%.asm}"
