package main

import (
	"fmt"

	"github.com/shirou/gopsutil/cpu"
	"github.com/shirou/gopsutil/mem"
)

func main() {
	memory, _ := mem.VirtualMemory()
	fmt.Printf("Total: %d, Free:%d, UsedPercent:%f%% \n", memory.Total, memory.Free, memory.UsedPercent)

	// convert to JSON. String() is also implemented
	fmt.Println(memory)

	fmt.Println()

	info, _ := cpu.Info()
	for _, stat := range info {
		fmt.Println(stat)
	}
}
