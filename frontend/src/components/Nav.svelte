<script>
	import { page } from '$app/stores';
	export let links;
</script>

<nav class="w-52 border-r-2 border-slate-200">
	{#each links as { url, svg, label, tab, isTab, bg }}
		<a
			href={url}
			class="w-full flex items-center py-5 hover:bg-slate-300 duration-200 {bg($page)}"
			class:font-bold={isTab($page)}
			class:hover:bg-slate-300={!isTab($page)}
		>
			<img
				src="/{svg}.svg"
				alt={svg}
				class="inline-block mx-5 w-5"
				class:opacity-50={!isTab($page)}
			/>
			<span class="inline-block">{label}</span>
		</a>
	{/each}
</nav>
