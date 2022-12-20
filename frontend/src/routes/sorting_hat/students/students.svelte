<script>
	import { studentsStore } from '../../../stores/students';
	import { Datatable } from 'svelte-simple-datatables';
	import DataTableElement from '../../../components/DataTableElement.svelte';
	const settings = {
		sortable: true,
		pagination: true,
		rowsPerPage: 50,
		columnFilter: true,
		blocks: {
			searchInput: true,
			paginationButtons: true,
			paginationRowCount: true
		}
	};

	let rows;
</script>

<Datatable {settings} data={[...$studentsStore]} bind:dataRows={rows} slot="main">
	<div class="datatable">
		<thead>
			<th data-key="sid">SID</th>
			<th data-key="name">Name</th>
			<th data-key="gender">Gender</th>
			<th data-key="house">House</th>
			<th data-key="do-group">Do-group</th>
			<th data-key="birth_date">Date of birth</th>
			<th data-key="nationality">Nationality</th>
			<th data-key="priorExperience">Prior Experience</th>
			<th data-key="start">Start Year</th>
		</thead>
		<tbody>
			{#if $rows}
				{#each $rows as row}
					<DataTableElement {row} />
				{/each}
			{/if}
		</tbody>
	</div>
</Datatable>

<style>
	.datatable {
		max-width: 65vw;
	}
</style>
