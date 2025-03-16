package main

import (
	"errors"
	"fmt"
	"strings"
)

func main() {
	err1 := errors.New("new error from errors")
	fmt.Println(err1)

	err2 := fmt.Errorf("new error from fmt")
	fmt.Println(err2)

	err3 := &NewError{}
	fmt.Println(err3)
	var err4 error
	err4 = err3
	fmt.Println(err4)

	wrapErrorTestErrors := firstError()
	fmt.Println(wrapErrorTestErrors)
	if errors.As(wrapErrorTestErrors, &wrapError) {
		fmt.Printf("wrapErrorTestErrors have wrapError type. wrapErrorTestErrors=%v\n", wrapErrorTestErrors)
	}

	if errors.Is(wrapErrorTestErrors, wrapError) {
		fmt.Printf("wrapErrorTestErrors have wrapError error. wrapErrorTestErrors=%v\n", wrapErrorTestErrors)
	}

	for true {
		err := errors.Unwrap(wrapErrorTestErrors)
		if err == nil {
			break
		}
		fmt.Println(err.Error())

		wrapErrorTestErrors = err
	}

	defer recoverMethod()
	panicMethod()
}

func panicMethod() error {
	panic("panic occurred!!!!!!!!!!!!!!!")
}

func recoverMethod() {
	v := recover()
	fmt.Println(v)
}

type NewError struct{}

func (e *NewError) Error() string {
	return "new error from errors"
}

func validate(param1 string, param2 int) (bool, error) {
	if len(strings.TrimSpace(param1)) == 0 {
		return false, fmt.Errorf("param1 is empty")
	}

	if param2 == 0 {
		return false, fmt.Errorf("param2 is zero")
	}

	return true, nil
}

func validateWithMultiErrors(param1 string, param2 int) (bool, error) {
	var errs error
	if len(strings.TrimSpace(param1)) == 0 {
		errs = errors.Join(errs, fmt.Errorf("param1 is empty"))
	}

	if param2 == 0 {
		errs = errors.Join(errs, fmt.Errorf("param2 is zero"))
	}

	return true, nil
}

func executeMultipleTry() (bool, error) {
	var errs error
	if err := firstExecute(); err != nil {
		errs = errors.Join(errs, err)
	}

	if err := secondExecute(); err != nil {
		errs = errors.Join(errs, err)
	}

	if errs != nil {
		return false, errs
	}

	return true, nil
}

func firstExecute() error {
	return errors.New("first execute error")
}

func secondExecute() error {
	return errors.New("second execute error")
}
