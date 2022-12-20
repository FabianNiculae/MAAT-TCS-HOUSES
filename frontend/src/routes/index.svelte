<script>
	import { studentsStore } from '../stores/students.js';
	import { Datatable } from 'svelte-simple-datatables';
	const settings = {
		sortable: true,
		pagination: true,
		rowsPerPage: 50,
		columnFilter: true
	};

	let rows;
</script>

<Datatable {settings} data={[...$studentsStore]} bind:dataRows={rows}>
	<thead>
		<th data-key="id">ID</th>
		<th data-key="sid">SID</th>
		<th data-key="name">Name</th>
	</thead>
	<tbody>
		{#if $rows}
			{#each $rows as row}
				<tr>
					<td>{row.id}</td>
					<td>{row.sid}</td>
					<td>{row.name}</td>
				</tr>
			{/each}
		{/if}
	</tbody>
</Datatable>
