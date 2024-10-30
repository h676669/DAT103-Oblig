#!/bin/bash

if [ "$#" -ne 2 ]; then
  echo "Usage: $0 print3digits.asm printfun.asm"
  exit 1
fi

task2_asm="$1"
task3_asm="$2"

student_name="Your Full Name"
student_id="676669"

echo "The name and studentID: $student_name, $student_id"

echo "Compiling $task2_asm"
nasm -f elf -F dwarf -g "$task2_asm" -o "${task2_asm%.asm}.o"
ld -m elf_i386 -o "${task2_asm%.asm}" "${task2_asm%.asm}.o"

echo "Executing $task2_asm"
./"${task2_asm%.asm}"

echo "Compiling $task3_asm"
nasm -f elf -F dwarf -g "$task3_asm" -o "${task3_asm%.asm}.o"
ld -m elf_i386 -o "${task3_asm%.asm}" "${task3_asm%.asm}.o"

echo "Executing $task3_asm"
./"${task3_asm%.asm}"
