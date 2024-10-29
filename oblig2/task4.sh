#!/bin/bash

# Get file names as parameters
asm1=$1
asm2=$2

# Print name and student ID
echo "The name and student ID: Edvard Steenslid, 676669" # Replace 'Your Name' and '123456' with your actual name and ID

# Compile and execute print3digits.asm
echo "Compiling $asm1"
nasm -f elf32 -F dwarf -g $asm1 && ld -m elf_i386 -o print3digits print3digits.o
if [ $? -eq 0 ]; then
  
  ./print3digits
  echo "Output of $asm1 finished"
  ./print3digits
else
  echo "Compilation of $asm1 failed"
  exit 1
fi

# Compile and execute printfun.asm
echo "Compiling $asm2"
nasm -f elf32 -F dwarf -g $asm2 && ld -m elf_i386 -o printfun printfun.o
if [ $? -eq 0 ]; then
  
  ./printfun
  echo "Output of $asm2 finished"
  ./printfun
else
  echo "Compilation of $asm2 failed"
  exit 1
fi
