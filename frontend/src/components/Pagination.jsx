function Pagination({
    page,
    totalPages,
    setPage})
{
return (
    <div style={{marginTop:"20px"}}>
            <button onClick={()=>setPage(page-1)} disabled={page === 0}>
                Previous
            </button>

            <span style={{margin : "0 10px"}}>
                Page{page+1} {" "} of {" "}{totalPages}
            </span>

            <button onClick={()=>setPage(page+1)} disabled={page>=totalPages-1}>
                Next
            </button>
    </div>
);
}
export default Pagination;