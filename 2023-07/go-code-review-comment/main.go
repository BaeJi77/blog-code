package main

import "fmt"

type Data struct {
	Value int
}

func (d *Data) ModifyValue(newValue int) {
	d.Value = newValue
}

func main() {
	data := Data{Value: 1}

	copiedData := data
	copiedData.ModifyValue(10) // This will not modify the original data

	// Print the values
	fmt.Println("Original Data:", data.Value)
	fmt.Println("Copied Data:", copiedData.Value)

	data2 := &Data{Value: 2}

	copiedData2 := data2
	copiedData2.ModifyValue(11) // This will modify the original data

	// Print the values
	fmt.Println("Original Data 2:", data2.Value)
	fmt.Println("Copied Data 2:", copiedData2.Value)
}
