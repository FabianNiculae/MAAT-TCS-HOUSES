<script>
	import { page } from '$app/stores';
	export let getLinks;
</script>

<div class="flex">
	{#each getLinks($page) as { url, label }}
		<div class:active={$page.url.pathname == url}>
			<a class="py-2 px-10 tab" href={url}>{label}</a>
		</div>
	{/each}
</div>

<style>
	.tab {
		transition: 0.2s;
	}
	a {
		width: 100%;
		height: 100%;
		display: block;
	}
	.active {
		border-bottom: 3px #1b998b solid;
		background-color: #f5f5f5;
		font-weight: 700;
	}
	.tab:not(.active):hover {
		cursor: pointer;
		background-color: #f9f9f9;
	}
</style>
