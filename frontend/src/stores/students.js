import { writable } from 'svelte/store';

export const studentsStore = writable([]);

const fetchStudents = async () => {
	const url = `http://localhost:3000/students`;
	const res = await fetch(url);
	const data = await res.json();
	console.log(data);
	const students = data.map((data) => {
		return {
			...data
		};
	});
	studentsStore.set(students);
};

fetchStudents();
