function FilterBar({
    isOpen,
    setIsOpen,

    filterMinPrice,
    setFilterMinPrice,

    filterMaxPrice,
    setFilterMaxPrice,

    filterStock,
    setFilterStock,

    sortBy,
    setSortBy,
    setPage
})
    {
    return(
        <>
        {
            isOpen &&
            <div className="drawer-overlay"
            onClick={()=>setIsOpen(false)}
            />
        }
        <div className={`filter-container ${isOpen?"open":""}`}>
            <div className="filter-drawer">
                <h3>Filters</h3>

                <input
                type="number"
                placeholder="Min Price"
                value={filterMinPrice}
                onChange={
                    (e)=>setFilterMinPrice(e.target.value)
                }/>

                <input
                type="number"
                placeholder="Max Price"
                value={filterMaxPrice}
                onChange={
                    (e)=>setFilterMaxPrice(e.target.value)
                }/>

                <input
                type="number"
                placeholder="Stock"
                value={filterStock}
                onChange={
                    (e)=>setFilterStock(e.target.value)
                }/>
</div>
                <button
                className="filter-handle-btn"
                onClick={()=>setIsOpen(!isOpen)}>
                    {isOpen ? "✕" : "Filters"}
                </button>
        </div>

        <div className="sort-container">
            <label>Sort By:</label>
            <select value={sortBy}
            onChange={(e) => {

                        setSortBy(e.target.value);

                        setPage(0);
                    }}
            >
                <option value="id">Default</option>
                <option value="price">Price Low to High</option>
                <option value="price,desc">Price High to Low</option>
            </select>

        </div>
        </>
    );
}
export default FilterBar;